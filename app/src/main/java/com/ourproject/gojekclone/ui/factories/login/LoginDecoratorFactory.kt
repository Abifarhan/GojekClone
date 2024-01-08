package com.ourproject.gojekclone.ui.factories.login

import com.ourproject.gojekclone.ui.decorator.LoginDecorator
import com.ourproject.login_domain.LoginUseCase
import com.ourproject.login_domain.UserSessionUseCase

class LoginDecoratorFactory {

    companion object{
        fun createLoginDecorator(
            decorator: LoginUseCase,
            local: UserSessionUseCase
            ) : LoginUseCase{
            return LoginDecorator(
                decorator, local
            )
        }
    }
}