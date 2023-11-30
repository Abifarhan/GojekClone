package com.ourproject.register_module

import app.cash.turbine.test
import com.ourproject.register_module.datasource.http.ConnectivityException
import com.ourproject.register_module.datasource.http.HttpClientResult
import com.ourproject.register_module.datasource.http.RegisterFeedRetrofitHttpClient
import com.ourproject.register_module.datasource.http.RegisterUserService
import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.datasource.http.dto.ResponseDataDto
import com.ourproject.register_module.datasource.http.usecase.BadRequestException
import io.mockk.CapturingSlot
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
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

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun testSubmitData() {
//        println("here the user data user ${userMocked}")
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
                    ResponseBody.create(null,"")
                )

                coEvery {
                    service.registerUser(capture(slot))
                } throws HttpException(response)

            }

            expectedResult is ConnectivityException -> {
                coEvery {
                    service.registerUser(capture(slot))
                } throws ConnectivityException()
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
                    println("success")
                }

                is HttpClientResult.Failure -> {
                    println("You are failed")
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

