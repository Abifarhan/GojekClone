package com.ourproject.gojekclone.ui.factories.remote.register

import com.ourproject.register_http.usecase.RegisterHttpClient
import com.ourproject.register_http.usecase.insfrastucture.RegisterRetrofitClient

class RegisterHttpClientFactory {

    companion object{
        fun createRegisterHttpClient(): RegisterHttpClient {
            return RegisterRetrofitClient(
                RegisterServiceFactory.createRegisterService()
            )
        }
    }
}