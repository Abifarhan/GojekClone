package com.ourproject.login_module.factories

import com.ourproject.login_module.feed.http.LoginFeedHttpClient
import com.ourproject.login_module.feed.http.LoginFeedRetrofitHttpClient

class LoginFeedHttpClientFactory {

    companion object {
        fun createLoginFeedHttpClient(): LoginFeedHttpClient {
            return LoginFeedRetrofitHttpClient(
                LoginFeedServiceFactory.createLoginFeedService()
            )
        }
    }
}