package com.ourproject.gojekclone.ui.factories.remote.register

import com.ourproject.core.framework.remote.HttpFactory
import com.ourproject.register_http.usecase.RegisterService

class RegisterServiceFactory {

    companion object{

        fun createRegisterService() : RegisterService {
            return HttpFactory.createRetrofit(
                HttpFactory.createMoshi(),
                HttpFactory.createOkhttpClient(
                    HttpFactory.createLoggingInterceptor()
                )
            ).create(RegisterService::class.java)
        }
    }
}