package com.ourproject.infrastructure.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("register")
    suspend fun register(
        @Body registerBody : RemoteRegisterRequest
    ) : RemoteRegisterResponse
}