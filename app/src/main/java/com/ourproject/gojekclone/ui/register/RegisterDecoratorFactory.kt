package com.ourproject.gojekclone.ui.register

import com.ourproject.gojekclone.ui.decorator.RegisterDecorator
import com.ourproject.register_domain.api.RegisterSubmit
import com.ourproject.register_domain.local.RegisterSaveSession

class RegisterDecoratorFactory {


    companion object {
        fun createRegisterDecorator(
            decorator: RegisterSubmit,
            cache: RegisterSaveSession
        ) : RegisterSubmit {

            return RegisterDecorator(
                decorator, cache
            )
        }
    }
}