package com.ourproject.register_module.factory

import com.ourproject.register_module.datasource.http.RegisterUserService
import com.ourproject.register_module.datasource.http.dto.RegistrationData

class RegisterServiceFactory {

    companion object{
        fun createRegisterUser(registrationData: RegistrationData) : RegisterUserService {
            return HttpRegisterFactory.createRetrofit(
                HttpRegisterFactory.createMoshi()
            ).create(RegisterUserService::class.java)
        }
    }
}