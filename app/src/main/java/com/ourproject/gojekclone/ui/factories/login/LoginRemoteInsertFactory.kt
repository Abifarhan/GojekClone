package com.ourproject.gojekclone.ui.factories.login

import com.ourproject.login_domain.LoginSubmit
import com.ourproject.login_http.RemoteLoginSubmit

class LoginRemoteInsertFactory {

    companion object {
        fun createLoginRemoteInsert(): LoginSubmit{
            return RemoteLoginSubmit(
                LoginHttpClientFactory.createLoginHttpClient()
            )
        }
    }
}