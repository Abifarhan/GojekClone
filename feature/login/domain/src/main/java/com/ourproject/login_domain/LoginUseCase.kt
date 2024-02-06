package com.ourproject.login_domain

import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    fun login(loginSubmit: LoginSubmitDomain) : Flow<SubmitResult>
}