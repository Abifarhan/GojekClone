package com.ourproject.register_module.viewmodel

import app.cash.turbine.test
import com.ourproject.register_module.datasource.http.RegisterFeedLoader
import com.ourproject.register_module.datasource.http.RegisterFeedResult
import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.datasource.http.usecase.BadRequest
import com.ourproject.register_module.datasource.http.usecase.Connectivity
import com.ourproject.register_module.datasource.http.usecase.InternalServerError
import com.ourproject.register_module.datasource.http.usecase.NotFound
import com.ourproject.register_module.domain.GofoodLoader
import com.ourproject.register_module.presentation.RegisterFeedViewModel
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RegisterViewModelTest {

    private val useCaseRegister = spyk<RegisterFeedLoader>()
    private val useCaseResultRegister = spyk<GofoodLoader>()

    private lateinit var sut: RegisterFeedViewModel

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        sut = RegisterFeedViewModel(goPayRegisterLoader = useCaseRegister, useCaseResultRegister)

        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun testSubmitNotLoad() {
        verify(exactly = 0) {
            useCaseRegister.submit(params)
        }

        confirmVerified(useCaseRegister)
    }

    @Test
    fun testSubmitRequestData() = runBlocking {
        every {
            useCaseRegister.submit(params)
        } returns flowOf()

        sut.submitUserRegister(params)

        verify(exactly = 1) {
            useCaseRegister.submit(params)
        }
        confirmVerified(useCaseRegister)
    }

    @Test
    fun testSubmitRequestDataTwice() = runBlocking {
        every {
            useCaseRegister.submit(params)
        } returns flowOf()

        sut.submitUserRegister(params)
        sut.submitUserRegister(params)

        verify(exactly = 2) {
            useCaseRegister.submit(params)
        }
        confirmVerified(useCaseRegister)
    }

    @Test
    fun testSubmitRequestFailedConnectivityShowsConnectivityError() = runBlocking {

        expected(
            result = RegisterFeedResult.Failure(Connectivity()),
            sut = sut,
            expectedFailedResult = "Tidak ada internet"
        )
    }

    @Test
    fun testSubmitRequestShowsBadRequestError() = runBlocking {
        expected(
            result = RegisterFeedResult.Failure(BadRequest()),
            sut = sut,
            expectedFailedResult = "Permintaan gagal, coba lagi"
        )
    }

    @Test
    fun testSubmitRequestShowsNotFoundError() = runBlocking {
        expected(
            result = RegisterFeedResult.Failure(NotFound()),
            sut = sut,
            expectedFailedResult =  "Tidak ditemukan, coba lagi"
        )
    }

    @Test
    fun testSubmitRequestShowsInternalServerError() = runBlocking {
        expected(
            result = RegisterFeedResult.Failure(InternalServerError()),
            sut = sut,
            expectedFailedResult =  "Server sedang dalam perbaikan"
        )
    }


    private fun expected(
        result: RegisterFeedResult,
        sut: RegisterFeedViewModel,
        expectedFailedResult: String
    ) = runBlocking {
        every {
            useCaseRegister.submit(params)
        } returns flowOf(result)

        sut.submitUserRegister(params)

        sut.isUserRegistered.take(1).test {
            val receivedResult = awaitItem()

            if (receivedResult.failedMessage.isNotEmpty()) {
                assertEquals(expectedFailedResult, receivedResult.failedMessage)
            } else {
                assertEquals(true, receivedResult.userRegistered)
            }

            awaitComplete()
        }

        verify(exactly = 1) {
            useCaseRegister.submit(params)
        }

        confirmVerified(useCaseRegister)
    }

}