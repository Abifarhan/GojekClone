package com.ourproject.login_http

import com.ourproject.login_domain.HttpClientResult
import kotlinx.coroutines.flow.Flow

interface LoginHttpClient {
    fun login(
        body: LoginSubmitDto
    ): Flow<HttpClientResult<LoginResultDto>>
}