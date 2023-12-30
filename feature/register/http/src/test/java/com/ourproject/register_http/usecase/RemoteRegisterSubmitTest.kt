package com.ourproject.register_http.usecase

import HttpClientResult
import SubmitResult
import app.cash.turbine.test
import com.ourproject.ConnectivityException
import com.ourproject.InternalServerErrorException
import com.ourproject.InvalidDataException
import com.ourproject.NotFoundExceptionException
import com.ourproject.UnexpectedException
import com.ourproject.register_domain.api.RegisterSubmitDto
import com.ourproject.register_domain.api.RemoteRegisterResponseDto
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

class RemoteRegisterSubmitTest{

    private val client = spyk<RegisterHttpClient>()
    private lateinit var sut: RemoteRegisterSubmit

    private val registerRequest = RegisterSubmitDto(
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
        sut = RemoteRegisterSubmit(client)
    }

    @Test
    fun testInitDoesNotRequestData(){
        verify(exactly = 0) {
            client.register(registerRequest)
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadRequestsData() = runBlocking{

        every {
            client.register(registerRequest)
        } returns flowOf()

        sut.register(registerRequest).test{
            awaitComplete()
        }

        verify(exactly = 1) {
            client.register(registerRequest)
        }

        confirmVerified(client)
    }
    @Test
    fun testLoadTwiceRequestsDataTwice() = runBlocking{

        every {
            client.register(registerRequest)
        } returns flowOf()

        sut.register(registerRequest).test{
            awaitComplete()
        }
        sut.register(registerRequest).test{
            awaitComplete()
        }

        verify(exactly = 2) {
            client.register(registerRequest)
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
            receivedHttpClientResult = HttpClientResult.Failure(ConnectivityException()),
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
            receivedHttpClientResult = HttpClientResult.Success(RemoteRegisterResponseDto.DEFAULT),
            expectedResult = SubmitResult.Success(registerRequest),
            exactly = 1
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun expect(
        sut: RemoteRegisterSubmit,
        receivedHttpClientResult: HttpClientResult<RemoteRegisterResponseDto>,
        expectedResult: Any,
        exactly: Int = -1,
    ) = runBlocking {

        every {
            client.register(registerRequest)
        } returns flowOf(receivedHttpClientResult)

        sut.register(registerRequest).test {
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
            client.register(registerRequest)
        }

        confirmVerified(client)

    }
}