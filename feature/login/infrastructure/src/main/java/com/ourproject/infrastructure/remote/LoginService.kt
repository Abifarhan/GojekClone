package com.ourproject.infrastructure.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("login")
    suspend fun login(
        @Body loginBody: RemoteLoginRequest
    ): RemoteLoginResponse
}