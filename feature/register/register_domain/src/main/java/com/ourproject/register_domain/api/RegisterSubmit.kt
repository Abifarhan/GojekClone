package com.ourproject.register_domain.api

import SubmitResult
import kotlinx.coroutines.flow.Flow

interface RegisterSubmit {
    fun register(registerSubmitDto : RegisterSubmitDto) : Flow<SubmitResult<RegisterSubmitDto>>
}