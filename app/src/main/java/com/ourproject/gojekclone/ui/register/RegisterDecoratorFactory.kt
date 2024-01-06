package com.ourproject.gojekclone.ui.register

import com.ourproject.gojekclone.ui.decorator.RegisterDecorator
import com.ourproject.register_domain.api.RegisterUserCase
import com.ourproject.session_user.domain.UserSessionUseCase

class RegisterDecoratorFactory {


    companion object {
        fun createRegisterDecorator(
            decorator: RegisterUserCase,
            cache: UserSessionUseCase
        ) : RegisterUserCase {

            return RegisterDecorator(
                decorator, cache
            )
        }
    }
}