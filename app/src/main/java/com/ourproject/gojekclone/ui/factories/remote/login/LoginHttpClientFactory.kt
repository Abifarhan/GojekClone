package com.ourproject.gojekclone.ui.factories.remote.login

import com.ourproject.login_http.LoginHttpClient
import com.ourproject.login_http.insfrastructure.LoginRetrofitClient

class LoginHttpClientFactory {

    companion object{
        fun createLoginHttpClient(): LoginHttpClient {
            return LoginRetrofitClient(
                LoginServiceFactory.createLoginService()
            )
        }
    }
}