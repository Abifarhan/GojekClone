package com.ourproject.login_presenter

import app.cash.turbine.test
import com.ourproject.login_domain.LoginUseCase
import com.ourproject.login_domain.LoginSubmitDomain
import com.ourproject.login_domain.SubmitResult
import com.ourproject.login_domain.UserDataDomain
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginViewModelTest{

    private val useCase = spyk<LoginUseCase>()

    private lateinit var sut: LoginViewModel

    private val params = LoginSubmitDomain(
        email = "birin2@gmail.com",
        password = "1234567890"
    )


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        sut = LoginViewModel(loginInsert = useCase)
        Dispatchers.setMain(UnconfinedTestDispatcher())

    }

    @Test
    fun testSubmitNotLoad() {
        verify(exactly = 0) {
            useCase.login(params)
        }

        confirmVerified(useCase)
    }

    @Test
    fun testSubmitLoginData() = runBlocking {
        every {
            useCase.login(params)
        } returns flowOf()

        useCase.login(params)

        verify(exactly = 1) {
            useCase.login(params)
        }
        confirmVerified(useCase)
    }


    @Test
    fun testSubmitLoginDataTwice() = runBlocking {
        every {
            useCase.login(params)
        } returns flowOf()

        useCase.login(params)
        useCase.login(params)

        verify(exactly = 1) {
            useCase.login(params)
        }
        confirmVerified(useCase)
    }

    @Test
    fun testSubmitRequestFailedConnectivityShowsConnectivityError() = runBlocking {

        expected(
            result = SubmitResult.Failure(Connectivity().message ?: ""),
            sut = sut,
            expectedFailedResult = "Tidak ada internet"
        )
    }

    @Test
    fun testSubmitRequestShowsBadRequestError() = runBlocking {
        expected(
            result = SubmitResult.Failure(BadRequest().message ?: ""),
            sut = sut,
            expectedFailedResult = "Permintaan gagal, coba lagi"
        )
    }

    @Test
    fun testSubmitRequestShowsNotFoundError() = runBlocking {
        expected(
            result = SubmitResult.Failure(NotFound().message ?: ""),
            sut = sut,
            expectedFailedResult =  "Tidak ditemukan, coba lagi"
        )
    }

    @Test
    fun testSubmitRequestShowsInternalServerError() = runBlocking {
        expected(
            result = SubmitResult.Failure(InternalServerError().message ?: ""),
            sut = sut,
            expectedFailedResult =  "Server sedang dalam perbaikan"
        )
    }

    @Test
    fun testSubmitRequestUnexpectedShowsError() = runBlocking {
        expected(
            result = SubmitResult.Failure(Unexpected().message ?: ""),
            sut = sut,
            expectedFailedResult =  "Terjadi kesalahan, coba lagi"
        )
    }

    @Test
    fun testSubmitRequestWithArgumentAndResult() = runBlocking {

        val slot = slot<LoginSubmitDomain>()
        expected(
            result = SubmitResult.Success(UserDataDomain.DEFAULT),
            sut = sut,
            expectedFailedResult = "",
            slot = slot
        )
    }
    private fun expected(
        result: SubmitResult,
        sut: LoginViewModel,
        expectedFailedResult: String,
        slot: CapturingSlot<LoginSubmitDomain> = slot<LoginSubmitDomain>()
    ) = runBlocking {
        every {
            useCase.login(capture(slot))
        } returns flowOf(result)

        useCase.login(params)

        sut.userDataLiveData.take(1).test {
            val receivedResult = awaitItem()

            if (receivedResult.failedMessage.isNotEmpty()) {
                assertEquals(expectedFailedResult, receivedResult.failedMessage)
            } else {
                assertEquals("birin1@gmail.com",slot.captured.email)
                assertEquals(true, receivedResult.userRegistered)
            }

            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.login(params)
        }

        confirmVerified(useCase)
    }
}

class BadRequest : Exception()
class NotFound : Exception()

class InternalServerError : Exception()

class Unexpected : Exception()
class Connectivity : Exception()