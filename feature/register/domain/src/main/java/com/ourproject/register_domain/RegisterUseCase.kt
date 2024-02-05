package com.ourproject.register_domain

import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {
    fun register(registerSubmitDto : RegisterSubmitDomain) : Flow<SubmitResult>
}