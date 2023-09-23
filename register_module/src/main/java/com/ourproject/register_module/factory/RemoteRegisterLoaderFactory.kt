package com.ourproject.register_module.factory

import android.util.Log
import com.ourproject.register_module.datasource.http.GoPayRegisterLoader
import com.ourproject.register_module.datasource.http.dto.RegistrationData
import com.ourproject.register_module.datasource.http.usecase.RemoteGopayRegisterLoader

open class RemoteRegisterLoaderFactory {

    companion object {
        fun createRemoteRegisterUserLoader(): GoPayRegisterLoader {

            return RemoteGopayRegisterLoader(
                RegisterUserHttpClientFactory.createRegisterUserHttpClient()
            )
        }
    }
}