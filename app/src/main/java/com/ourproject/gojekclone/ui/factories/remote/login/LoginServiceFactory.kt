package com.ourproject.gojekclone.ui.factories.remote.login

import com.ourproject.core.framework.remote.HttpFactory
import com.ourproject.infrastructure.remote.LoginService

class LoginServiceFactory {

    companion object{
        fun createLoginService(): com.ourproject.infrastructure.remote.LoginService {
            return HttpFactory.createRetrofit(
                HttpFactory.createMoshi(),
                HttpFactory.createOkhttpClient(
                    HttpFactory.createLoggingInterceptor()
                )
            ).create(com.ourproject.infrastructure.remote.LoginService::class.java)
        }
    }
}