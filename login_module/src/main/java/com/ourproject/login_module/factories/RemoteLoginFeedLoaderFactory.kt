package com.ourproject.login_module.factories

import com.ourproject.login_module.feed.domain.LoginFeedLoader
import com.ourproject.login_module.feed.http.RemoteLoginFeedLoader

class RemoteLoginFeedLoaderFactory {

    companion object{
        fun createRemoteLoginFeedLoader(): LoginFeedLoader {
            return RemoteLoginFeedLoader(
                LoginFeedHttpClientFactory.createLoginFeedHttpClient()
            )
        }
    }
}