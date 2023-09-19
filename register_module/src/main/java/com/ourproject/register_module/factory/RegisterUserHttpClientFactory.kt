package com.ourproject.register_module.factory

import com.ourproject.register_module.datasource.http.RegisterRetrofitHttpClient
import com.ourproject.register_module.datasource.http.dto.RegistrationData

class RegisterUserHttpClientFactory {

    companion object{
        fun createRegisterUserHttpClient(registrationData: RegistrationData): RegisterRetrofitHttpClient {
            return RegisterRetrofitHttpClient(
                RegisterServiceFactory.createRegisterUser(registrationData)
            )
        }
    }
}