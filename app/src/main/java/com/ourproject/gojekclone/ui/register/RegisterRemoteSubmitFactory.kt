package com.ourproject.gojekclone.ui.register

import com.ourproject.register_domain.api.RegisterSubmit

class RegisterRemoteSubmitFactory {

    companion object {

        fun createRegisterRemoteSubmit(): RegisterSubmit {
            return RemoteRegisterSubmit(
                RegisterHttpClientFactory.cr
            )
        }
    }
}