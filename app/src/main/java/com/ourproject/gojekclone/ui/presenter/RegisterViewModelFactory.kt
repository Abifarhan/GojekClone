package com.ourproject.gojekclone.ui.presenter

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ourproject.gojekclone.ui.factories.local.register.LocalSessionInsertFactory
import com.ourproject.gojekclone.ui.register.RegisterDecoratorFactory
import com.ourproject.gojekclone.ui.register.RegisterRemoteSubmitFactory
import com.ourproject.register_presenter.RegisterViewModel

class RegisterViewModelFactory {


    companion object {

        fun createRegisterViewModelFactory() : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RegisterViewModel(
                    RegisterDecoratorFactory.createRegisterDecorator(
                        decorator = RegisterRemoteSubmitFactory.createRegisterRemoteSubmit(),
                        cache = LocalSessionInsertFactory.createLocalSession()
                    )
                )
            }
        }
    }
}