package com.ourproject.register_http.usecase

import kotlinx.coroutines.flow.Flow

interface RegisterHttpClient {

    fun register(
        body: RegisterSubmitRequest
    ) : Flow<HttpClientResult>
}