package com.ourproject.gojekclone.ui.factories.login

import com.ourproject.login_domain.LoginInsert
import com.ourproject.login_http.RemoteLoginInsert

class LoginRemoteInsertFactory {

    companion object {
        fun createLoginRemoteInsert(): LoginInsert{
            return RemoteLoginInsert(
                LoginHttpClientFactory.createLoginHttpClient()
            )
        }
    }
}