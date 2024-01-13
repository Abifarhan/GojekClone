package com.ourproject.gojekclone.ui.factories.remote.login

import com.ourproject.gojekclone.ui.framework.HttpFactory
import com.ourproject.login_http.LoginService

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