package com.ourproject.login_http

import com.ourproject.login_http.insfrastructure.RemoteLoginResponseDto

sealed class HttpClientResult{

    data class Success(val root: RemoteLoginResponseDto) : HttpClientResult()

    data class Failure(val throwable: Throwable) : HttpClientResult()
}