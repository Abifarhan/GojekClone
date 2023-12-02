package com.ourproject.login_module.feed.presentation

import app.cash.turbine.test
import com.ourproject.login_module.feed.domain.LoginFeedLoader
import com.ourproject.login_module.feed.domain.LoginFeedResult
import com.ourproject.login_module.feed.domain.LoginSubmitEntity
import com.ourproject.login_module.feed.http.LoginSubmitDto
import com.ourproject.register_module.datasource.http.RegisterFeedResult
import com.ourproject.register_module.datasource.http.usecase.BadRequest
import com.ourproject.register_module.datasource.http.usecase.Connectivity
import com.ourproject.register_module.datasource.http.usecase.InternalServerError
import com.ourproject.register_module.datasource.http.usecase.NotFound
import com.ourproject.register_module.domain.GofoodLoader
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

import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private val useCase = spyk<LoginFeedLoader>()
    private val useCaseResult = spyk<GofoodLoader>()

    private lateinit var sut: LoginViewModel

    private val params = LoginSubmitEntity(
        email = "birin2@gmail.com",
        password = "1234567890"
    )

    private val loginRequest = LoginSubmitDto(
        email = params.email,
        password = params.password
    )


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        sut = LoginViewModel(loginFeedLoader = useCase, gopayResulRegisterLoader = useCaseResult)
        Dispatchers.setMain(UnconfinedTestDispatcher())

    }

    @Test
    fun testSubmitNotLoad() {
        verify(exactly = 0) {
            useCase.submit(params)
        }

        confirmVerified(useCase)
    }

    @Test
    fun testSubmitLoginData() = runBlocking {
        every {
            useCase.submit(params)
        } returns flowOf()

        sut.submitDataUser(params)

        verify(exactly = 1) {
            useCase.submit(params)
        }
        confirmVerified(useCase)
    }


    @Test
    fun testSubmitLoginDataTwice() = runBlocking {
        every {
            useCase.submit(params)
        } returns flowOf()

        sut.submitDataUser(params)
        sut.submitDataUser(params)

        verify(exactly = 1) {
            useCase.submit(params)
        }
        confirmVerified(useCase)
    }

    @Test
    fun testSubmitRequestFailedConnectivityShowsConnectivityError() = runBlocking {

        expected(
            result = LoginFeedResult.Failure(Connectivity()),
            sut = sut,
            expectedFailedResult = "Tidak ada internet"
        )
    }

    @Test
    fun testSubmitRequestShowsBadRequestError() = runBlocking {
        expected(
            result = LoginFeedResult.Failure(BadRequest()),
            sut = sut,
            expectedFailedResult = "Permintaan gagal, coba lagi"
        )
    }

    @Test
    fun testSubmitRequestShowsNotFoundError() = runBlocking {
        expected(
            result = LoginFeedResult.Failure(NotFound()),
            sut = sut,
            expectedFailedResult =  "Tidak ditemukan, coba lagi"
        )
    }

    @Test
    fun testSubmitRequestShowsInternalServerError() = runBlocking {
        expected(
            result = LoginFeedResult.Failure(InternalServerError()),
            sut = sut,
            expectedFailedResult =  "Server sedang dalam perbaikan"
        )
    }

    private fun expected(
        result: LoginFeedResult,
        sut: LoginViewModel,
        expectedFailedResult: String,
        slot: CapturingSlot<LoginSubmitEntity> = slot<LoginSubmitEntity>()
    ) = runBlocking {
        every {
            useCase.submit(capture(slot))
        } returns flowOf(result)

        sut.submitDataUser(params)

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
            useCase.submit(capture(slot))
        }

        confirmVerified(useCase)
    }
}