package com.ourproject.register_http.usecase

import HttpClientResult
import com.ourproject.register_http.usecase.dto.RegisterSubmitDto
import com.ourproject.register_http.usecase.dto.RemoteRegisterResponseDto
import kotlinx.coroutines.flow.Flow

interface RegisterHttpClient {

    fun register(
        body: RegisterSubmitDto
    ) : Flow<HttpClientResult<RemoteRegisterResponseDto>>
}