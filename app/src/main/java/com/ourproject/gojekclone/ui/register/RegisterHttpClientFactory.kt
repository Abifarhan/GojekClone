package com.ourproject.gojekclone.ui.register

import com.ourproject.register_http.usecase.RegisterHttpClient
import com.ourproject.register_http.usecase.RegisterRetrofitClient

class RegisterHttpClientFactory {


    companion object{
        fun createRegisterHttpClient():RegisterHttpClient {
            return RegisterRetrofitClient(
                RegisterServiceFactory.createRegisterService()
            )
        }
    }
}