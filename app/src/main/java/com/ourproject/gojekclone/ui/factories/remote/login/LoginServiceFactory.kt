package com.ourproject.gojekclone.ui.factories.remote.login

import com.ourproject.core.framework.remote.HttpFactory
import com.ourproject.login_http.LoginService

class LoginServiceFactory {

    companion object{
        fun createLoginService(): LoginService {
            return com.ourproject.core.framework.remote.HttpFactory.createRetrofit(
                com.ourproject.core.framework.remote.HttpFactory.createMoshi(),
                com.ourproject.core.framework.remote.HttpFactory.createOkhttpClient(
                    com.ourproject.core.framework.remote.HttpFactory.createLoggingInterceptor()
                )
            ).create(LoginService::class.java)
        }
    }
}