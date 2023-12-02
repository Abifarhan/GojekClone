package com.ourproject.login_module.feed.http

import app.cash.turbine.test
import com.ourproject.login_module.feed.domain.LoginFeedResult
import com.ourproject.login_module.feed.domain.LoginSubmitEntity
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class RemoteLoginFeedLoaderTest {

    private val client = spyk<LoginFeedHttpClient>()
    private lateinit var sut: RemoteLoginFeedLoader


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

        MockKAnnotations.init(this, relaxed = true)

        sut = RemoteLoginFeedLoader(loginFeedHttpClient = client)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun testSubmitDoesNotRequestLogin() {
        verify(exactly = 0) {
            client.submitLogin(loginRequest)
        }

        confirmVerified(client)
    }

    @Test
    fun testSubmitLoginUserDataOnce() = runBlocking {
        every {
            client.submitLogin(loginRequest)
        } returns flowOf()


        sut.submit(params).test {
            awaitComplete()
        }

        verify(exactly = 1) {
            client.submitLogin(loginRequest)
        }

        confirmVerified(client)

    }

    @Test
    fun testSubmitLoginConnectivityErrorOnClientError() = runBlocking {
        expected(
            sut = sut,
            receivedResult = HttpClientResult.Failure(ConnectivityException()),
            expectedResult = Connectivity(),
            exactly = 1
        )
    }

    @Test
    fun testSubmitDeliverInvalidDataError() = runBlocking {
        expected(
            sut = sut,
            receivedResult = HttpClientResult.Failure(InvalidDataException()),
            expectedResult = InvalidData(),
            exactly = 1
        )
    }

    @Test
    fun testSubmitLoginUserBadRequest() = runBlocking {
        expected(
            sut = sut,
            receivedResult = HttpClientResult.Failure(BadRequestException()),
            expectedResult = BadRequest(),
            exactly = 1
        )
    }

    @Test
    fun testSubmitLoginDeliversNotFoundErrorOnClientError() = runBlocking {
        expected(
            sut = sut,
            receivedResult = HttpClientResult.Failure(NotFoundException()),
            expectedResult = NotFound(),
            exactly = 1
        )
    }

    @Test
    fun testSubmitDeliversInternalServerError() = runBlocking {
        expected(
            sut = sut,
            receivedResult = HttpClientResult.Failure(InternalServerErrorException()),
            expectedResult = InternalServerError(),
            exactly = 1
        )
    }


    private fun expected(
        sut: RemoteLoginFeedLoader,
        receivedResult : HttpClientResult,
        expectedResult: Any,
        exactly: Int = -1,
        slot: CapturingSlot<LoginSubmitDto> = slot<LoginSubmitDto>()
    ) = runBlocking {
        every {
            client.submitLogin(capture(slot))
        } returns flowOf(receivedResult)


        sut.submit(params).test {
            val currentResult = try {
                awaitItem()
            } catch (e: Throwable) {
                null
            }

            currentResult?.let {
                when(it){
                    is LoginFeedResult.Success -> {
                        Assert.assertEquals("birin2@gmail.com", slot.captured.email)
                    }

                    is LoginFeedResult.Failure -> {
                        Assert.assertEquals(expectedResult::class.java, it.throwable::class.java)
                    }
                }
            }

            awaitComplete()
        }


        verify(exactly = exactly) {
            client.submitLogin(loginRequest)
        }

        confirmVerified(client)
    }
}

class BadRequestException : Exception()
class NotFoundException : Exception()
class InternalServerErrorException : Exception()



class BadRequest : Exception()
class NotFound : Exception()

class InternalServerError : Exception()


