package com.ourproject.infrastructure

import app.cash.turbine.test
import com.ourproject.infrastructure.remote.RegisterRetrofitClient
import com.ourproject.infrastructure.remote.RegisterService
import com.ourproject.infrastructure.remote.RemoteRegisterRequest
import com.ourproject.infrastructure.remote.RemoteRegisterResponse
import com.ourproject.register_domain.ConnectivityException
import com.ourproject.register_domain.InternalServerErrorException
import com.ourproject.register_domain.InvalidDataException
import com.ourproject.register_domain.NotFoundExceptionException
import com.ourproject.register_domain.UnexpectedException
import com.ourproject.register_http.usecase.HttpClientResult
import com.ourproject.register_http.usecase.RegisterSubmitRequest

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class RegisterRetrofitClientTest{

    private val service = mockk<RegisterService>()
    private lateinit var sut: RegisterRetrofitClient


    private val registerRequest = RemoteRegisterRequest(
        name = "birin",
        email = "birin1@gmail.com",
        password = "1234567890",
        passwordConfirmation = "1234567890",
        address = "Jakarta Pusat",
        city = "Jakarta",
        houseNumber = "3",
        phoneNumber = "5"
    )

    private val dtoToUseCase = RegisterSubmitRequest(
        name = "birin",
        email = "birin1@gmail.com",
        password = "1234567890",
        password_confirmation = "1234567890",
        address = "Jakarta Pusat",
        city = "Jakarta",
        houseNumber = "3",
        phoneNumber = "5"
    )

    @Before
    fun setUp(){
        sut = RegisterRetrofitClient(service)
    }

    @Test
    fun testGetFailsOnConnectivityError() = runBlocking {
        expect(
            sut = sut,
            expectedResult = ConnectivityException()
        )
    }

    @Test
    fun testGetFailsOn404HttpResponse() = runBlocking{
        expect(
            withStatusCode = 404,
            sut = sut,
            expectedResult = NotFoundExceptionException()
        )
    }
    @Test
    fun testGetFailsOn422HttpResponse() = runBlocking{
        expect(
            withStatusCode = 422,
            sut = sut,
            expectedResult = InvalidDataException()
        )
    }

    @Test
    fun testGetFailsOn500HttpResponse() = runBlocking{
        expect(
            withStatusCode = 500,
            sut = sut,
            expectedResult = InternalServerErrorException()
        )
    }
    @Test
    fun testGetFailsOnUnexpectedException() = runBlocking{
        expect(
            sut = sut,
            expectedResult = UnexpectedException()
        )
    }

    private fun expect(
        withStatusCode: Int? = null,
        sut: RegisterRetrofitClient,
        expectedResult: Any
    ) = runBlocking {

        when{
            withStatusCode != null -> {
                val response = Response.error<RemoteRegisterResponse>(
                    withStatusCode,
                    "".toResponseBody(null)
                )

                coEvery {
                    service.register(registerRequest)
                } throws HttpException(response)
            }

            expectedResult is ConnectivityException -> {
                coEvery {
                    service.register(registerRequest)
                } throws IOException()
            }
        }


        sut.register(dtoToUseCase).test {
            when(val receivedResult = awaitItem()){
                is HttpClientResult.Success -> {
                    assertEquals(expectedResult, receivedResult)
                }
                is HttpClientResult.Failure -> {
                    TestCase.assertEquals(expectedResult::class.java, receivedResult.throwable::class.java)
                }
            }
            awaitComplete()
        }

        coVerify(exactly = 1) {
            service.register(registerRequest)
        }
        confirmVerified(service)
    }

}