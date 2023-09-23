package com.ourproject.register_module.factory

import com.ourproject.register_module.datasource.http.RegisterUserService
import com.ourproject.register_module.frameworks.HttpRegisterFactory

class RegisterServiceFactory {

    companion object{
        fun createRegisterUser() : RegisterUserService {
            return HttpRegisterFactory.createRetrofit(
                HttpRegisterFactory.createMoshi()
            ).create(RegisterUserService::class.java)
        }
    }
}