package com.ourproject.gojekclone.ui.decorator

import SubmitResult
import com.ourproject.register_domain.api.RegisterSubmit
import com.ourproject.register_domain.api.RegisterSubmitDto
import com.ourproject.register_domain.api.RemoteRegisterResponseDto
import com.ourproject.register_domain.local.RegisterSaveSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterDecorator(
    private val decorator: RegisterSubmit,
    private val local: RegisterSaveSession
) : RegisterSubmit{

    override fun register(registerSubmitDto: RegisterSubmitDto): Flow<SubmitResult<RemoteRegisterResponseDto>> {
        return flow{
            decorator.register(registerSubmitDto).collect{ response ->
                if (response is SubmitResult.Success) {
                    local.insertUserSession(registerSubmitDto.email)
                }
                emit(response)
            }
        }
    }
}