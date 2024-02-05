package com.ourproject.register_presenter

import com.ourproject.session_user.SubmitResult
import app.cash.turbine.test
import com.ourproject.register_domain.RegisterUseCase
import com.ourproject.register_domain.RegisterSubmitDomain
import com.ourproject.register_domain.UserDataDomain
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

class RegisterViewModelTest{

    private val useCaseRegister = spyk<RegisterUseCase>()
    private lateinit var sut: RegisterViewModel

    private val params = RegisterSubmitDomain(
        name = "birin",
        email = "birin1@gmail.com",
        password = "1234567890",
        password_confirmation = "1234567890",
        address = "Jakarta Pusat",
        city = "Jakarta",
        houseNumber = "3",
        phoneNumber = "5"
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        sut = RegisterViewModel(registerSubmit = useCaseRegister)

        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun testSubmitNotLoad() {
        verify(exactly = 0) {
            useCaseRegister.register(params)
        }

        confirmVerified(useCaseRegister)
    }

    @Test
    fun testSubmitRequestData() = runBlocking {
        every {
            useCaseRegister.register(params)
        } returns flowOf()

        useCaseRegister.register(params)

        verify(exactly = 1) {
            useCaseRegister.register(params)
        }
        confirmVerified(useCaseRegister)
    }

    @Test
    fun testSubmitRequestDataTwice() = runBlocking {
        every {
            useCaseRegister.register(params)
        } returns flowOf()

        useCaseRegister.register(params)
        useCaseRegister.register(params)

        verify(exactly = 2) {
            useCaseRegister.register(params)
        }
        confirmVerified(useCaseRegister)
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

        val slot = slot<RegisterSubmitDomain>()
        expected(
            result = SubmitResult.Success(UserDataDomain.DEFAULT),
            sut = sut,
            expectedFailedResult = "",
            slot = slot
        )
    }

    private fun expected(
        result: SubmitResult<UserDataDomain>,
        sut: RegisterViewModel,
        expectedFailedResult: String,
        slot: CapturingSlot<RegisterSubmitDomain> = slot<RegisterSubmitDomain>()
    ) = runBlocking {
        every {
            useCaseRegister.register(capture(slot))
        } returns flowOf(result)

        useCaseRegister.register(params)

        sut.isUserRegistered.take(1).test {
            val receivedResult = awaitItem()

            if (receivedResult.failedMessage.isNotEmpty()) {
                assertEquals(expectedFailedResult, receivedResult.failedMessage)
            } else {
                assertEquals("birin",slot.captured.name)
                assertEquals(true, receivedResult.userRegistered)
            }

            awaitComplete()
        }

        verify(exactly = 1) {
            useCaseRegister.register(params)
        }

        confirmVerified(useCaseRegister)
    }
}

class BadRequestException : Exception()
class NotFoundException : Exception()
class InternalServerErrorException : Exception()

class UnexpectedException : Exception()




class BadRequest : Exception()
class NotFound : Exception()

class InternalServerError : Exception()

class Unexpected : Exception()
class Connectivity : Exception()