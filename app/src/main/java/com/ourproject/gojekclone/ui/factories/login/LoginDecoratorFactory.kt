package com.ourproject.gojekclone.ui.factories.login

import com.ourproject.login_domain.LoginSubmit
import com.ourproject.register_domain.local.RegisterSaveSession

class LoginDecoratorFactory {

    companion object{
        fun createLoginDecorator(
            decorator: LoginSubmit,
            local: RegisterSaveSession
            ) : LoginSubmit{
            return LoginLocalDecorator(
                decorator, local
            )
        }
    }
}