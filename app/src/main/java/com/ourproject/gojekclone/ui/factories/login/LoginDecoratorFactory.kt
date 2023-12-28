package com.ourproject.gojekclone.ui.factories.login

import com.ourproject.login_domain.LoginInsert
import com.ourproject.register_domain.local.RegisterSaveSession

class LoginDecoratorFactory {

    companion object{
        fun createLoginDecorator(
            decorator: LoginInsert,
            local: RegisterSaveSession
            ) : LoginInsert{
            return LoginLocalDecorator(
                decorator, local
            )
        }
    }
}