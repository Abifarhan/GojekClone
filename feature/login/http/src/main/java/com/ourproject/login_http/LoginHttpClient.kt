package com.ourproject.login_http

import com.ourproject.session_user.HttpClientResult
import kotlinx.coroutines.flow.Flow

interface LoginHttpClient {
    fun login(
        body: LoginSubmitDto
    ): Flow<HttpClientResult<LoginResultDto>>
}