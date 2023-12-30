package com.ourproject.login_http

import app.cash.turbine.test
import com.ourproject.ConnectivityException
import com.ourproject.InternalServerErrorException
import com.ourproject.InvalidDataException
import com.ourproject.NotFoundExceptionException
import com.ourproject.UnexpectedException
import com.ourproject.login_domain.LoginSubmitEntity
import com.ourproject.login_domain.LoginSubmitResultEntity
import com.ourproject.login_domain.UserDomain
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

class LoginRetrofitClientTest {

    private val service = mockk<LoginService>()
    private lateinit var sut: LoginRetrofitClient


    private val loginSubmit = LoginSubmitEntity(
        email = "default@gmail.com",
        password = "password12345"
    )

    private val convertDto = LoginSubmitDto(
        email = loginSubmit.email,
        password = loginSubmit.password
    )

    @Before
    fun setUp(){
        sut = LoginRetrofitClient(service)
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
        sut: LoginRetrofitClient,
        expectedResult: Any
    ) = runBlocking {

        when{
            withStatusCode != null -> {
                val response = Response.error<UserDomain>(
                    withStatusCode,
                    "".toResponseBody(null)
                )

                coEvery {
                    service.login(convertDto)
                } throws HttpException(response)
            }

            expectedResult is ConnectivityException -> {
                coEvery {
                    service.login(convertDto)
                } throws IOException()
            }
        }


        sut.login(convertDto).test {
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
            service.login(convertDto)
        }
        confirmVerified(service)
    }
}