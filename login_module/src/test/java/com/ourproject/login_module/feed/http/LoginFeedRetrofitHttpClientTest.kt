package com.ourproject.login_module.feed.http

import com.ourproject.login_module.feed.domain.LoginSubmitEntity
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.Assert.*

import org.junit.After
import org.junit.Before

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



    @After
    fun tearDown() {
        clearAllMocks()
    }
}