package com.ourproject.gojekclone.ui.factories.remote.login

import com.ourproject.login_domain.LoginUseCase
import com.ourproject.login_http.RemoteLoginUseCase

class LoginRemoteInsertFactory {

    companion object {
        fun createLoginRemoteInsert(): LoginUseCase{
            return RemoteLoginUseCase(
                LoginHttpClientFactory.createLoginHttpClient()
            )
        }
    }
}