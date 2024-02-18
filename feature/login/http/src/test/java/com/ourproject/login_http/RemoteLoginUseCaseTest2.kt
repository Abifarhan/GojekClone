package com.ourproject.login_http

import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Flow

sealed class HttpClientResul2{
    data class Success(val data: String): HttpClientResul2()

    data class
}

interface LoginHttpClient2{
    fun login(
        emai: String,
        password: String
    ) : Flow<HttpClientResul2>
}

class RemoteLoginUseCase2(val client : LoginHttpClient2){

}

data class LoginSubmitDomain2(
    val email: String,
    val password: String
)
class RemoteLoginUseCaseTest2 {


    private val client = spyk<LoginHttpClient2>()
    private lateinit var sut : RemoteLoginUseCase2

    private val loginSubmitData = LoginSubmitDomain2(
        email = "birin@gmail.com",
        password = "12345678"
    )

    @Before
    fun setUp() {
        sut = RemoteLoginUseCase2(client)
    }

    @Test
    fun testnitDoesNotRequestData(){
        verify {
            client.login(
                email = loginSubmitData.email,
                password = loginSubmitData.password
            )
        }
    }
}