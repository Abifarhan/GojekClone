package com.ourproject.register_http.usecase

import com.ourproject.register_http.usecase.dto.RegisterSubmitDto
import com.ourproject.register_http.usecase.dto.RemoteRegisterResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("register")
    suspend fun register(
        @Body registerBody : RegisterSubmitDto
    ) : RemoteRegisterResponseDto
}