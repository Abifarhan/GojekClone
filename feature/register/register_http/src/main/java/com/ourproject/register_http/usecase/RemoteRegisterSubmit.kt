package com.ourproject.register_http.usecase

import com.ourproject.register_domain.api.RegisterSubmit
import com.ourproject.register_domain.api.RegisterSubmitDto
import kotlinx.coroutines.flow.Flow

class RemoteRegisterSubmit(
    private val registerHttpClient: RegisterHttpClient
) : RegisterSubmit {
    override fun register(registerSubmitDto: RegisterSubmitDto): Flow<SubmitResult<RegisterSubmitDto>> {
        TODO("Not yet implemented")
    }


}