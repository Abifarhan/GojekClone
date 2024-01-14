package com.ourproject.gojekclone.ui.factories.remote.register

import com.ourproject.core.framework.remote.HttpFactory
import com.ourproject.register_http.usecase.RegisterService

class RegisterServiceFactory {

    companion object{

        fun createRegisterService() : RegisterService {
            return com.ourproject.core.framework.remote.HttpFactory.createRetrofit(
                com.ourproject.core.framework.remote.HttpFactory.createMoshi(),
                com.ourproject.core.framework.remote.HttpFactory.createOkhttpClient(
                    com.ourproject.core.framework.remote.HttpFactory.createLoggingInterceptor()
                )
            ).create(RegisterService::class.java)
        }
    }
}