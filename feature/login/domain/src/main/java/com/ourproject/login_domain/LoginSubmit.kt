package com.ourproject.login_domain

import SubmitResult
import kotlinx.coroutines.flow.Flow

interface LoginSubmit {
    fun login(loginSubmit: LoginSubmitEntity) : Flow<SubmitResult<UserDomain>>
}