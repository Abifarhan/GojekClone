package com.ourproject.register_module.factory

import com.ourproject.register_module.datasource.http.RegisterFeedLoader
import com.ourproject.register_module.datasource.http.usecase.RemoteRegisterFeedLoader

open class RemoteRegisterLoaderFactory {

    companion object {
        fun createRemoteRegisterUserLoader(): RegisterFeedLoader {

            return RemoteRegisterFeedLoader(
                RegisterUserHttpClientFactory.createRegisterUserHttpClient()
            )
        }
    }
}