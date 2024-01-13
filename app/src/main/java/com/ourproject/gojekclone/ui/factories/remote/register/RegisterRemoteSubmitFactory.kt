package com.ourproject.gojekclone.ui.factories.remote.register

import com.ourproject.register_domain.api.RegisterUserCase
import com.ourproject.register_http.usecase.RemoteRegisterUserCase

class RegisterRemoteSubmitFactory {

    companion object {

        fun createRegisterRemoteSubmit(): RegisterUserCase {
            return RemoteRegisterUserCase(
                RegisterHttpClientFactory.createRegisterHttpClient()
            )
        }
    }
}