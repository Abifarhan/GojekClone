package com.ourproject.gojekclone.ui.factories.remote.login

import com.ourproject.gojekclone.ui.decorator.LoginDecorator
import com.ourproject.login_domain.LoginUseCase
import com.ourproject.login_domain.UserSessionUseCase
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Provides

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