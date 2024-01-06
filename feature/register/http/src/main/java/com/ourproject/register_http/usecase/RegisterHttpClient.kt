package com.ourproject.register_http.usecase

import com.ourproject.session_user.HttpClientResult
import com.ourproject.register_http.usecase.dto.RegisterSubmitDto
import com.ourproject.register_http.usecase.dto.RemoteRegisterResponseDto
import kotlinx.coroutines.flow.Flow

interface RegisterHttpClient {

    fun register(
        body: RegisterSubmitDto
    ) : Flow<HttpClientResult<RemoteRegisterResponseDto>>
}