package com.ourproject.login_http

import HttpClientResult
import com.ourproject.login_domain.LoginSubmitEntity
import kotlinx.coroutines.flow.Flow

interface LoginHttpClient {
    fun login(
        body: LoginSubmitDto
    ): Flow<HttpClientResult<LoginResultDto>>
}