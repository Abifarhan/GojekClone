package com.ourproject.register_module

import app.cash.turbine.test
import com.ourproject.register_module.datasource.http.ConnectivityException
import com.ourproject.register_module.datasource.http.HttpClientResult
import com.ourproject.register_module.datasource.http.InvalidDataException
import com.ourproject.register_module.datasource.http.RegisterFeedRetrofitHttpClient
import com.ourproject.register_module.datasource.http.RegisterUserService
import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.datasource.http.dto.ResponseDataDto
import com.ourproject.register_module.datasource.http.usecase.BadRequestException
import com.ourproject.register_module.datasource.http.usecase.NotFoundException
import com.ourproject.register_module.datasource.http.usecase.UnexpectedException
import io.mockk.CapturingSlot
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class RegisterFeedRetrofitHttpClientTest {

    private val service = mockk<RegisterUserService>()
    private lateinit var sut: RegisterFeedRetrofitHttpClient
    var userData = mockk<RegistrationDto>()

    private val params = RegistrationEntity(
        name = "birin",
        email = "birin1@gmail.com",
        password = "1234567890",
        password_confirmation = "1234567890",
        address = "Jakarta Pusat",
        city = "Jakarta",
        houseNumber = "3",
        phoneNumber = "5"
    )

    private val registerRequest = RegistrationDto(
        name = params.name,
        email = params.email,
        password = params.password,
        password_confirmation = params.password_confirmation,
        address = params.address,
        city = params.city,
        houseNumber = params.houseNumber,
        phoneNumber = params.phoneNumber
    )

    @Before
    fun setUp() {
        sut = RegisterFeedRetrofitHttpClient(registerUserService = service)
    }


    @Test
    fun testGetFailsOnConnectivityError() = runBlocking {
        val slot = slot<RegistrationDto>()
        expect(
            sut = sut,
            expectedResult = ConnectivityException(),
            slot = slot
        )
    }


    @Test
    fun testGetFailsOn400HttpResponse() {
        expect(
            sut = sut,
            withStatusCode = 400,
            expectedResult = BadRequestException()
        )
    }

    @Test
    fun testGetFailsOn404HttpResponse() {
        expect(
            withStatusCode = 404,
            sut = sut,
            expectedResult = NotFoundException()
        )
    }

    @Test
    fun testGetFailsOn422HttpResponse() {
        expect(
            withStatusCode = 422,
            sut = sut,
            expectedResult = InvalidDataException()
        )
    }

    @Test
    fun testGetFailsOnUnexpectedException() {
        expect(
            sut = sut,
            expectedResult = UnexpectedException()
        )
    }

    @Test
    fun testGetSuccessOn200HttpResponseWithResponse() {
        expect(
            sut = sut,
            receivedResult = ResponseDataDto.DEFAULT,
            expectedResult = HttpClientResult.Success(
               ResponseDataDto.DEFAULT
            )
        )
    }


    @After
    fun tearDown() {
        clearAllMocks()
    }
    private fun expect(
        withStatusCode: Int? = null,
        sut: RegisterFeedRetrofitHttpClient,
        receivedResult: Any? = null,
        expectedResult: Any,
        slot: CapturingSlot<RegistrationDto> = slot<RegistrationDto>()
    ) = runBlocking {

        when {
            withStatusCode != null -> {
                val response = Response.error<ResponseDataDto>(
                    withStatusCode,
                    "".toResponseBody(null)
                )

                coEvery {
                    service.registerUser(capture(slot))
                } throws HttpException(response)

            }

            expectedResult is ConnectivityException -> {
                coEvery {
                    service.registerUser(capture(slot))
                } throws IOException()
            }

            expectedResult is HttpClientResult.Success -> {
                coEvery {
                    service.registerUser(capture(slot))
                } returns  receivedResult as ResponseDataDto
            }

            else -> {
                coEvery {
                    service.registerUser(
                        capture(slot)
                    )
                } throws Exception()
            }
        }

        sut.submitRegister(registerRequest).test {

            when (val receivedResult = awaitItem()){
                is HttpClientResult.Success -> {
                    println("success executed")
                    assertEquals(expectedResult, receivedResult)
                }

                is HttpClientResult.Failure -> {
                    println("failed executed")
                    assertEquals(expectedResult::class.java, receivedResult.throwable::class.java)
                }
            }
            awaitComplete()
        }


        coVerify(exactly = 1) {
            service.registerUser(registerRequest)
        }

        confirmVerified(service)
    }

}
