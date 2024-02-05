package com.ourproject.register_http.usecase


sealed class HttpClientResult{

    data class Success(val root: RegisterSubmitResponse) : HttpClientResult()

    data class Failure(val throwable: Throwable) : HttpClientResult()
}