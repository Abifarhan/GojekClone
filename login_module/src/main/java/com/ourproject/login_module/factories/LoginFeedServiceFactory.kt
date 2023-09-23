package com.ourproject.login_module.factories

import com.ourproject.login_module.feed.http.LoginFeedService
import com.ourproject.login_module.frameworks.LoginHttpFactory

class LoginFeedServiceFactory {

    companion object{
        fun createLoginFeedService(): LoginFeedService {
            return LoginHttpFactory.createRetrofit(
                LoginHttpFactory.createMoshi()
            ).create(LoginFeedService::class.java)
        }
    }
}