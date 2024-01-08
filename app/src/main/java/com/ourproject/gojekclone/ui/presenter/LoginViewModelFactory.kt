package com.ourproject.gojekclone.ui.presenter

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ourproject.gojekclone.ui.factories.local.login.LocalLoginSessionInsertFactory
import com.ourproject.gojekclone.ui.factories.local.register.LocalSessionInsertFactory
import com.ourproject.gojekclone.ui.factories.login.LoginDecoratorFactory
import com.ourproject.gojekclone.ui.factories.login.LoginRemoteInsertFactory

class LoginViewModelFactory {

    companion object {
        fun createLoginViewModel() : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                com.ourproject.login_presenter.LoginViewModel(
                    LoginDecoratorFactory.createLoginDecorator(
                        decorator = LoginRemoteInsertFactory.createLoginRemoteInsert(),
                        local = LocalLoginSessionInsertFactory.createLocalSession()
                    )
                )
            }
        }
    }
}