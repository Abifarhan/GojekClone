package com.ourproject.login_module.feed.http

import app.cash.turbine.test
import com.ourproject.login_module.feed.domain.LoginSubmitEntity
import com.ourproject.register_module.datasource.http.ConnectivityException
import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.usecase.BadRequestException
import io.mockk.CapturingSlot
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class LoginFeedRetrofitHttpClientTest {

    private val service = mockk<LoginFeedService>()
    private lateinit var sut: LoginFeedRetrofitHttpClient

    private val params = LoginSubmitEntity(
        email = "birin2@gmail.com",
        password = "1234567890"
    )

    private val loginRequest = LoginSubmitDto(
        email = params.email,
        password = params.password
    )
    @Before
    fun setUp() {

        sut = LoginFeedRetrofitHttpClient(loginFeedService = service)
    }

    @Test
    fun testGetFailsOnConnectivityError() = runBlocking {
        val slot = slot<LoginSubmitDto>()
        expect(
            sut = sut,
            expectedResult = ConnectivityException(),
            slot = slot
        )
    }


    @Test
    fun testGetFailsOn400HttpResponse() = runBlocking {
        val slot = slot<LoginSubmitDto>()
        expect(
            sut = sut,
            withStatusCode = 400,
            expectedResult = BadRequestException(),
            slot = slot
        )
    }


    @Test
    fun testGetFailsOn404HttpResponse() = runBlocking {
        val slot = slot<LoginSubmitDto>()
        expect(
            sut = sut,
            withStatusCode = 404,
            expectedResult = BadRequestException(),
            slot = slot
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun expect(
        withStatusCode: Int? = null,
        sut: LoginFeedRetrofitHttpClient,
        receivedResult: Any? = null,
        expectedResult: Any,
        slot: CapturingSlot<LoginSubmitDto> = slot<LoginSubmitDto>()
    ) = runBlocking {

        when {
            withStatusCode != null -> {
                val response = Response.error<LoginResultDto>(
                    withStatusCode,
                    ResponseBody.create(null,"")
                )

                coEvery {
                    service.submit(capture(slot))
                } throws HttpException(response)

            }

            expectedResult is ConnectivityException -> {
                coEvery {
                    service.submit(capture(slot))
                } throws ConnectivityException()
            }

            expectedResult is HttpClientResult.Success -> {
                coEvery {
                    service.submit(capture(slot))
                } returns  Response.success(receivedResult as LoginResultDto)
            }

            else -> {
                coEvery {
                    service.submit(
                        capture(slot)
                    )
                } throws Exception()
            }
        }

        sut.submitLogin(loginRequest).test {

            when (val receivedResult = awaitItem()){
                is HttpClientResult.Success -> {
                    assertEquals(expectedResult, receivedResult.root.data.user.email)
                }

                is HttpClientResult.Failure -> {
                    assertEquals(expectedResult, receivedResult.throwable.message)

                }
            }
            awaitComplete()
        }


        coVerify(exactly = 1) {
            service.submit(loginRequest)
        }

        confirmVerified(service)
    }
}