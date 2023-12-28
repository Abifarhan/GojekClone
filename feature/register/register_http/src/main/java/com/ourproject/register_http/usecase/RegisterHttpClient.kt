package com.ourproject.register_http.usecase

import HttpClientResult
import com.ourproject.register_domain.api.RegisterSubmitDto
import com.ourproject.register_domain.api.RemoteRegisterResponseDto
import kotlinx.coroutines.flow.Flow

interface RegisterHttpClient {

    fun register(
        body: RegisterSubmitDto
    ) : Flow<HttpClientResult<RemoteRegisterResponseDto>>
}