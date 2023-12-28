package com.ourproject.login_domain

import SubmitResult
import kotlinx.coroutines.flow.Flow

interface LoginInsert {
    fun login(loginSubmit: LoginSubmitEntity) : Flow<SubmitResult<LoginSubmitResultEntity>>
}