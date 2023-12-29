package com.ourproject.gojekclone.ui.factories.login

import SubmitResult
import com.ourproject.login_domain.LoginSubmit
import com.ourproject.login_domain.LoginSubmitEntity
import com.ourproject.login_domain.LoginSubmitResultEntity
import com.ourproject.register_domain.local.RegisterSaveSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginLocalDecorator(
    private val decorator: LoginSubmit,
    private val local: RegisterSaveSession
) : LoginSubmit {
    override fun login(loginSubmitDto: LoginSubmitEntity): Flow<SubmitResult<LoginSubmitResultEntity>> {
        return flow {
            decorator.login(loginSubmitDto).collect{ response ->
                if (response is SubmitResult.Success){
                    local.insertUserSession(loginSubmitDto.email)
                }
                emit(response)
            }
        }
    }

}