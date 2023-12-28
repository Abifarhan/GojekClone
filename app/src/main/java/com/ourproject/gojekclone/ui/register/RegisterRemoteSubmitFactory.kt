package com.ourproject.gojekclone.ui.register

import com.ourproject.register_domain.api.RegisterSubmit
import com.ourproject.register_http.usecase.RemoteRegisterSubmit

class RegisterRemoteSubmitFactory {

    companion object {

        fun createRegisterRemoteSubmit(): RegisterSubmit {
            return RemoteRegisterSubmit(
                RegisterHttpClientFactory.createRegisterHttpClient()
            )
        }
    }
}