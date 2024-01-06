package com.ourproject.gojekclone.ui.decorator

import com.ourproject.session_user.SubmitResult
import com.ourproject.register_domain.api.RegisterUserCase
import com.ourproject.register_domain.api.RegisterSubmitEntity
import com.ourproject.session_user.domain.UserSessionUseCase
import com.ourproject.register_domain.local.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterDecorator(
    private val decorator: RegisterUserCase,
    private val local: UserSessionUseCase
) : RegisterUserCase{

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