package com.ourproject.register_domain.api

import com.ourproject.register_domain.SubmitResult
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {
    fun register(registerSubmitDto : RegisterSubmitEntity) : Flow<SubmitResult>
}