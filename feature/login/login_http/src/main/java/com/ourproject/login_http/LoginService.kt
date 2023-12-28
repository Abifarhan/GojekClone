package com.ourproject.login_http

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("login")
    suspend fun login(
        @Body loginBody: LoginSubmitDto
    ): LoginResultDto
}