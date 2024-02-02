package com.ourproject.gojekclone.ui.factories.remote.login

import com.ourproject.core.framework.remote.HttpFactory
import com.ourproject.login_http.insfrastructure.LoginService

class LoginServiceFactory {

    companion object{
        fun createLoginService(): LoginService {
            return HttpFactory.createRetrofit(
                HttpFactory.createMoshi(),
                HttpFactory.createOkhttpClient(
                    HttpFactory.createLoggingInterceptor()
                )
            ).create(LoginService::class.java)
        }
    }
}