package com.ourproject.gojekclone.ui.presenter

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ourproject.gojekclone.ui.factories.local.login.LocalLoginSessionInsertFactory
import com.ourproject.gojekclone.ui.factories.remote.login.LoginDecoratorFactory
import com.ourproject.gojekclone.ui.factories.remote.login.LoginRemoteInsertFactory

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