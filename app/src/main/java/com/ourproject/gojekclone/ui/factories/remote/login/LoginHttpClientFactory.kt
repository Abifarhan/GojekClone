package com.ourproject.gojekclone.ui.factories.remote.login

import com.ourproject.infrastructure.remote.LoginRetrofitClient
import com.ourproject.login_http.LoginHttpClient
import dagger.Binds
import dagger.Module

@Module
abstract class LoginHttpClientFactory {

    @Binds
    abstract fun createLoginHttpClient(
        loginRetrofitClient: LoginRetrofitClient
    ) : LoginHttpClient
}