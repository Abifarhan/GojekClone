package com.ourproject.gojekclone.ui.factories.remote.register

import com.ourproject.gojekclone.ui.decorator.RegisterDecorator
import com.ourproject.register_domain.api.RegisterUserCase
import com.ourproject.register_domain.usecase.UserSessionUseCase

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