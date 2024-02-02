package com.ourproject.login_http.insfrastructure

import com.ourproject.login_http.LoginSubmitDto
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("login")
    suspend fun login(
        @Body loginBody: LoginSubmitDto
    ): RemoteLoginResponseDto
}