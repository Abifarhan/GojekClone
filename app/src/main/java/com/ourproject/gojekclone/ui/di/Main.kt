package com.ourproject.gojekclone.ui.di

import com.ourproject.core.framework.remote.HttpFactory
import com.ourproject.gojekclone.ui.factories.remote.login.LoginHttpClientFactory
import com.ourproject.gojekclone.ui.factories.remote.login.LoginRemoteInsertFactory
import com.ourproject.gojekclone.ui.factories.remote.login.LoginServiceFactory
import com.ourproject.infrastructure.remote.LoginRetrofitClient
import com.ourproject.login_http.RemoteLoginUseCase
import com.ourproject.login_presenter.LoginViewModel
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        LoginRemoteInsertFactory::class,
        LoginHttpClientFactory::class,
        LoginServiceFactory::class,
        HttpFactory::class
    ]
)
@Singleton
interface MainComponent{

    @Component.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun loginViewModel(): LoginViewModel
}