package com.ourproject.register_module.factory

import com.ourproject.register_module.datasource.http.RegisterFeedRetrofitHttpClient
import com.ourproject.register_module.datasource.http.dto.RegistrationData

class RegisterUserHttpClientFactory {

    companion object{
        fun createRegisterUserHttpClient(): RegisterFeedRetrofitHttpClient {
            return RegisterFeedRetrofitHttpClient(
                RegisterServiceFactory.createRegisterUser()
            )
        }
    }
}