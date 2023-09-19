package com.ourproject.register_module.factory

import android.util.Log
import com.ourproject.register_module.datasource.http.GoPayRegisterLoader
import com.ourproject.register_module.datasource.http.dto.RegistrationData
import com.ourproject.register_module.datasource.http.usecase.RemoteGopayRegisterLoader

open class RemoteRegisterLoaderFactory {

    companion object {
        fun createRemoteRegisterUserLoader(registrationData: RegistrationData): GoPayRegisterLoader {

            Log.d("TAG", "createRemoteRegisterUserLoader: this executed heheheh")
            return RemoteGopayRegisterLoader(
                RegisterUserHttpClientFactory.createRegisterUserHttpClient(registrationData)
            )
        }
    }
}