package com.ourproject.login_http

import HttpClientResult
import SubmitResult
import app.cash.turbine.test
import com.ourproject.ConnectivityException
import com.ourproject.InternalServerErrorException
import com.ourproject.InvalidDataException
import com.ourproject.NotFoundExceptionException
import com.ourproject.UnexpectedException
import com.ourproject.login_domain.LoginSubmitEntity
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RemoteLoginSubmitTest{

    private val client = spyk<LoginHttpClient>()
    private lateinit var sut: RemoteLoginSubmit

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
        sut = RemoteLoginSubmit(client)
    }

    @Test
    fun testInitDoesNotRequestData(){
        verify(exactly = 0) {
            client.login(convertDto)
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadRequestsData() = runBlocking{

        every {
            client.login(convertDto)
        } returns flowOf()

        sut.login(loginSubmit).test{
            awaitComplete()
        }

        verify(exactly = 1) {
            client.login(convertDto)
        }

        confirmVerified(client)
    }
    @Test
    fun testLoadTwiceRequestsDataTwice() = runBlocking{

        every {
            client.login(convertDto)
        } returns flowOf()

        sut.login(loginSubmit).test{
            awaitComplete()
        }
        sut.login(loginSubmit).test{
            awaitComplete()
        }

        verify(exactly = 2) {
            client.login(convertDto)
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadDeliversInvalidDataError() = runBlocking {
        expect(
            sut = sut,
            receivedHttpClientResult = HttpClientResult.Failure(InvalidDataException()),
            expectedResult = "Invalid Data",
            exactly = 1
        )
    }
    @Test
    fun testLoadDeliversConnectivityError() = runBlocking {
        expect(
            sut = sut,
            receivedHttpClientResult =  HttpClientResult.Failure(ConnectivityException()),
            expectedResult = "Connectivity",
            exactly = 1
        )
    }
    @Test
    fun testLoadDeliversNotFoundError() = runBlocking {
        expect(
            sut = sut,
            receivedHttpClientResult = HttpClientResult.Failure(NotFoundExceptionException()),
            expectedResult = "Not Found",
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversInternalServerError() = runBlocking {
        expect(
            sut = sut,
            receivedHttpClientResult = HttpClientResult.Failure(InternalServerErrorException()),
            expectedResult = "Internal Server Error",
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversUnexpectedError() = runBlocking {
        expect(
            sut = sut,
            receivedHttpClientResult = HttpClientResult.Failure(UnexpectedException()),
            expectedResult = "Something went wrong",
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversItemsOn200HttpResponseWithResponse() {


        expect(
            sut = sut,
            receivedHttpClientResult = HttpClientResult.Success(LoginResultDto.DEFAULT),
            expectedResult = SubmitResult.Success(RemoteLoginData.DEFAULT),
            exactly = 1
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun expect(
        sut: RemoteLoginSubmit,
        receivedHttpClientResult: HttpClientResult<LoginResultDto>,
        expectedResult: Any,
        exactly: Int = -1,
    ) = runBlocking {

        every {
            client.login(convertDto)
        } returns flowOf(receivedHttpClientResult)

        sut.login(loginSubmit).test {
            when(val receivedResult = awaitItem()){
                is SubmitResult.Success -> {
                    assertEquals(expectedResult::class.java, receivedResult::class.java)
                }
                is SubmitResult.Failure -> {
                    assertEquals(expectedResult, receivedResult.errorMessage)
                }
            }
            awaitComplete()
        }

        verify(exactly = exactly) {
            client.login(convertDto)
        }

        confirmVerified(client)

    }
}