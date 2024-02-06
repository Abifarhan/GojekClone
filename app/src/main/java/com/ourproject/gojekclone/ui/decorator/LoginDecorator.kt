package com.ourproject.gojekclone.ui.decorator

import com.ourproject.login_domain.LoginUseCase
import com.ourproject.login_domain.LoginSubmitDomain
import com.ourproject.login_domain.SubmitResult
import com.ourproject.login_domain.UserSessionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginDecorator(
    private val decorator: LoginUseCase,
    private val local: UserSessionUseCase
) : LoginUseCase {
    override fun login(loginSubmit: LoginSubmitDomain): Flow<SubmitResult> {
        return flow {
            decorator.login(loginSubmit).collect{result ->
                if (result is SubmitResult.Success){

                    local.insertUserSession(result.data)
                }
                emit(result)
            }
        }
    }

}