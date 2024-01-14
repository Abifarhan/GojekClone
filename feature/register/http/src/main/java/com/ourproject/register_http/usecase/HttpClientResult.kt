package com.ourproject.register_http.usecase

import com.ourproject.register_http.usecase.dto.RemoteRegisterResponseDto

sealed class HttpClientResult{

    data class Success(val root: RemoteRegisterResponseDto) : HttpClientResult()

    data class Failure(val throwable: Throwable) : HttpClientResult()
}