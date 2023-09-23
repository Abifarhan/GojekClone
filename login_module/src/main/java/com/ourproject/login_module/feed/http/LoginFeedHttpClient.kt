package com.ourproject.login_module.feed.http

import kotlinx.coroutines.flow.Flow


sealed class HttpClientResult{
    data class Success(val root: LoginResultDto): HttpClientResult()

    data class Failure(val throwable: Throwable): HttpClientResult()
}
interface LoginFeedHttpClient {

    fun submitLogin(loginSubmitDto: LoginSubmitDto) : Flow<HttpClientResult>
}

class InvalidDataException : Throwable()
class ConnectivityException : Throwable()