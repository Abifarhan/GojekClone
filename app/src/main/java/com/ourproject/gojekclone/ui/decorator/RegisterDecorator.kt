package com.ourproject.gojekclone.ui.decorator

import com.ourproject.register_domain.SubmitResult
import com.ourproject.register_domain.api.RegisterUserCase
import com.ourproject.register_domain.api.RegisterSubmitEntity
import com.ourproject.register_domain.local.UserEntity
import com.ourproject.register_domain.usecase.UserSessionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterDecorator(
    private val decorator: RegisterUserCase,
    private val local: UserSessionUseCase
) : RegisterUserCase{

    override fun register(registerSubmit: RegisterSubmitEntity): Flow<SubmitResult> {
        return flow{
            decorator.register(registerSubmit).collect{ result ->
                if (result is SubmitResult.Success) {
                    local.insertUserSession(result.data.email)
                }
                emit(result)
            }
        }
    }
}