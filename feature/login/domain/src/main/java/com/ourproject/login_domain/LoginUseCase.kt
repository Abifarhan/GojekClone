package com.ourproject.login_domain

import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    fun login(loginSubmit: LoginSubmitEntity) : Flow<SubmitResult<UserDomain>>
}