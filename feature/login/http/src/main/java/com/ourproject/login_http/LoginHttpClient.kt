package com.ourproject.login_http

import kotlinx.coroutines.flow.Flow

interface LoginHttpClient {
    fun login(
        body: LoginSubmitRequest
    ): Flow<HttpClientResult>
}