package com.ourproject.register_domain.api

import com.ourproject.session_user.SubmitResult
import com.ourproject.register_domain.local.UserEntity
import kotlinx.coroutines.flow.Flow

interface RegisterUserCase {
    fun register(registerSubmitDto : RegisterSubmitEntity) : Flow<SubmitResult<UserEntity>>
}