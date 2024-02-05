package com.ourproject.gojekclone.ui.decorator

import com.ourproject.register_domain.SubmitResult
import com.ourproject.register_domain.RegisterUseCase
import com.ourproject.register_domain.RegisterSubmitDomain
import com.ourproject.register_domain.UserSessionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterDecorator(
    private val decorator: RegisterUseCase,
    private val local: UserSessionUseCase
) : RegisterUseCase {

    override fun register(registerSubmit: RegisterSubmitDomain): Flow<SubmitResult> {
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