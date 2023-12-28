package com.ourproject.gojekclone.ui.factories.login

import com.ourproject.login_http.LoginHttpClient
import com.ourproject.login_http.LoginRetrofitClient

class LoginHttpClientFactory {

    companion object{
        fun createLoginHttpClient(): LoginHttpClient {
            return LoginRetrofitClient(
                LoginServiceFactory.createLoginService()
            )
        }
    }
}