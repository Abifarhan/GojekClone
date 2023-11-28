package com.ourproject.register_module.datasource.http.usecase

import app.cash.turbine.test
import com.ourproject.register_module.datasource.http.HttpClientResult
import com.ourproject.register_module.datasource.http.RegisterFeedHttpClient
import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.datasource.http.dto.ResponseDataDto
import com.ourproject.register_module.datasource.http.dto.UserLocal
import com.ourproject.register_module.domain.GofoodRegisterLocalResult
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


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
        every {
            client.submitRegister(capture(slot))
        }returns flowOf()


        sut.submit(userData = params).test {
            assertEquals("birin", slot.captured.name)
            assertEquals("1234567890", slot.captured.password)
            assertEquals(receivedResult.root.data.user.email, expectedResult.userData.email)
            awaitComplete()
        }

        verify(exactly = 1) {
            client.submitRegister(registerRequest)
        }

        confirmVerified(client)
    }


}