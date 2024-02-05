package com.ourproject.infrastructure.remote

import com.ourproject.infrastructure.remote.RemoteRegisterRequest
import com.ourproject.login_http.RemoteRegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("register")
    suspend fun register(
        @Body registerBody : RemoteRegisterRequest
    ) : RemoteRegisterResponse
}