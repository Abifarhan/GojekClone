package com.ourproject.login_module.feed.http

import com.ourproject.login_module.feed.domain.LoginSubmitEntity
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.spyk
import io.mockk.verify
import org.junit.After
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
            client.submitLogin()
        }

        confirmVerified(client)
    }
}