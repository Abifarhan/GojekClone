package com.ourproject.login_domain

import com.ourproject.session_user.SubmitResult
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    fun login(loginSubmit: LoginSubmitEntity) : Flow<SubmitResult<UserDomain>>
}