package com.ourproject.login_http

sealed class HttpClientResult{

    data class Success(val root: RemoteLoginResponseDto) : HttpClientResult()

    data class Failure(val throwable: Throwable) : HttpClientResult()
}