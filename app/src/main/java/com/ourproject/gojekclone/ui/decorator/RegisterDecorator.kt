package com.ourproject.gojekclone.ui.decorator

import SubmitResult
import com.ourproject.register_domain.api.RegisterSubmit
import com.ourproject.register_domain.api.RegisterSubmitEntity
import com.ourproject.register_domain.local.RegisterSaveSession
import com.ourproject.register_domain.local.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterDecorator(
    private val decorator: RegisterSubmit,
    private val local: RegisterSaveSession
) : RegisterSubmit{

    override fun register(registerSubmitDto: RegisterSubmitEntity): Flow<SubmitResult<UserEntity>> {
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