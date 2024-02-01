package com.ourproject.gojekclone.ui.factories.remote.register

import com.ourproject.register_domain.api.RegisterUseCase
import com.ourproject.register_http.usecase.RemoteRegisterUseCase

class RegisterRemoteSubmitFactory {

    companion object {

        fun createRegisterRemoteSubmit(): RegisterUseCase {
            return RemoteRegisterUseCase(
                RegisterHttpClientFactory.createRegisterHttpClient()
            )
        }
    }
}