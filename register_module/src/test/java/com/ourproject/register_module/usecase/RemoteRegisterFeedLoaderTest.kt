package com.ourproject.register_module.usecase

import app.cash.turbine.test
import com.ourproject.register_module.datasource.http.ConnectivityException
import com.ourproject.register_module.datasource.http.HttpClientResult
import com.ourproject.register_module.datasource.http.RegisterFeedHttpClient
import com.ourproject.register_module.datasource.http.RegisterFeedResult
import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.datasource.http.dto.ResponseDataDto
import com.ourproject.register_module.datasource.http.dto.UserLocal
import com.ourproject.register_module.datasource.http.usecase.BadRequest
import com.ourproject.register_module.datasource.http.usecase.BadRequestException
import com.ourproject.register_module.datasource.http.usecase.Connectivity
import com.ourproject.register_module.datasource.http.usecase.InternalServerError
import com.ourproject.register_module.datasource.http.usecase.InternalServerErrorException
import com.ourproject.register_module.datasource.http.usecase.InvalidData
import com.ourproject.register_module.datasource.http.usecase.InvalidDataException
import com.ourproject.register_module.datasource.http.usecase.NotFound
import com.ourproject.register_module.datasource.http.usecase.NotFoundException
import com.ourproject.register_module.datasource.http.usecase.RemoteRegisterFeedLoader
import com.ourproject.register_module.datasource.http.usecase.Unexpected
import com.ourproject.register_module.datasource.http.usecase.UnexpectedException
import com.ourproject.register_module.domain.GofoodRegisterLocalResult
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.math.exp


class RemoteRegisterFeedLoaderTest{
    private val client = spyk<RegisterFeedHttpClient>()
    private lateinit var sut: RemoteRegisterFeedLoader


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
        MockKAnnotations.init(this, relaxed = true)

        sut = RemoteRegisterFeedLoader(registerRetrofitHttpClient = client)
    }


    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun testSubmitDoesNotRequestRegister() {
        verify(exactly = 0) {
            client.submitRegister(registerRequest)
        }

        confirmVerified(client)
    }
    @Test
    fun testSubmitRegisterUserDataOnce() = runBlocking {
        every {
            client.submitRegister(registerRequest)
        } returns flowOf()


        sut.submit(params).test {
            awaitComplete()
        }

        verify(exactly = 1) {
            client.submitRegister(registerRequest)
        }

        confirmVerified(client)

    }

    @Test
    fun testSubmitRegisterConnectivityErrorOnClientError() = runBlocking {
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
    fun testSubmitRegisterUserBadRequest() = runBlocking {
        expected(
            sut = sut,
            receivedResult = HttpClientResult.Failure(BadRequestException()),
            expectedResult = BadRequest(),
            exactly = 1
        )
    }

    @Test
    fun testSubmitRegisterDeliversNotFoundErrorOnClientError() = runBlocking {
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


    @Test
    fun testSubmitRegisterUnexpectedError() = runBlocking {
        expected(
            sut = sut,
            receivedResult = HttpClientResult.Failure(UnexpectedException()),
            expectedResult = Unexpected(),
            exactly = 1
        )
    }

    @Test
    fun testSubmitRegisterUserDataTwice() = runBlocking {
        every {
            client.submitRegister(registerRequest)
        } returns flowOf()


        sut.submit(params).test {
            awaitComplete()
        }

        sut.submit(params).test {
            awaitComplete()
        }

        verify(exactly = 2) {
            client.submitRegister(registerRequest)
        }

        confirmVerified(client)
    }
    @Test
    fun registerWithArgumentsWithResult() = runBlocking {

        val slot = slot<RegistrationDto>()
        val receivedResult = HttpClientResult.Success(ResponseDataDto.DEFAULT)
        val expectedResult = GofoodRegisterLocalResult.Success(UserLocal.DEFAULT)

        expected(
            sut = sut,
            receivedResult = receivedResult,
            expectedResult = expectedResult.userData.email,
            exactly = 1,
            slot = slot
        )

    }

    private fun expected(
        sut: RemoteRegisterFeedLoader,
        receivedResult : HttpClientResult,
        expectedResult: Any,
        exactly: Int = -1,
        slot: CapturingSlot<RegistrationDto> = slot<RegistrationDto>()
    ) = runBlocking {
        every {
            client.submitRegister(capture(slot))
        } returns flowOf(receivedResult)


        sut.submit(userData = params).test {
            val currentResult = try {
                awaitItem()
            } catch (e: Throwable) {
                null
            }

            currentResult?.let {
                when(it){
                    is RegisterFeedResult.Success -> {
                        assertEquals("birin", slot.captured.name)
                        assertEquals(expectedResult, it.root.data.user.email)
                    }

                    is RegisterFeedResult.Failure -> {
                        assertEquals(expectedResult::class.java, it.throwable::class.java)
                    }
                }
            }

            awaitComplete()
        }


        verify(exactly = exactly) {
            client.submitRegister(registerRequest)
        }

        confirmVerified(client)
    }

}