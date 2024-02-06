package com.ourproject.gojekclone.ui.factories.local.login

import com.ourproject.core.framework.local.LocalFactory
import com.ourproject.infrastructure.session.UserLoginSessionPreferenceClient
import com.ourproject.session.usecase.LoginPreferenceClient

class LocalLoginSessionFactory {

    companion object{

        fun createLocalSessionInsert(): LoginPreferenceClient {
            return UserLoginSessionPreferenceClient(
                LocalFactory.createPreference()
            )
        }
    }
}