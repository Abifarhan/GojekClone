package com.ourproject.gojekclone.ui.factories.remote.login

import com.ourproject.infrastructure.remote.LoginRetrofitClient
import com.ourproject.login_http.LoginHttpClient

class LoginHttpClientFactory {

    companion object{
        fun createLoginHttpClient(): LoginHttpClient {
            return LoginRetrofitClient(
                LoginServiceFactory.createLoginService()
            )
        }
    }
}