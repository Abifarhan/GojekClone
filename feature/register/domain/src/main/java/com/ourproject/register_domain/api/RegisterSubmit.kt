package com.ourproject.register_domain.api

import SubmitResult
import com.ourproject.register_domain.local.UserEntity
import kotlinx.coroutines.flow.Flow

interface RegisterSubmit {
    fun register(registerSubmitDto : RegisterSubmitEntity) : Flow<SubmitResult<UserEntity>>
}