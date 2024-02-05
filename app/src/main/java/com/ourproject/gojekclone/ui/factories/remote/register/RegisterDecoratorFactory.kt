package com.ourproject.gojekclone.ui.factories.remote.register

import com.ourproject.gojekclone.ui.decorator.RegisterDecorator
import com.ourproject.register_domain.RegisterUseCase
import com.ourproject.register_domain.UserSessionUseCase

class RegisterDecoratorFactory {

    companion object {
        fun createRegisterDecorator(
            decorator: RegisterUseCase,
            cache: UserSessionUseCase
        ) : RegisterUseCase {

            return RegisterDecorator(
                decorator, cache
            )
        }
    }
}