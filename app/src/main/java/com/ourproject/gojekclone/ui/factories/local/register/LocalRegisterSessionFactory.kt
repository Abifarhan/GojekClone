package com.ourproject.gojekclone.ui.factories.local.register

import com.ourproject.core.framework.local.LocalFactory
import com.ourproject.infrastructure.session.UserRegisterSessionPreferenceClient
import com.ourproject.register_cache.RegisterPreferenceClient

class LocalRegisterSessionFactory {

    companion object{

        fun createLocalSessionInsert(): RegisterPreferenceClient {
            return UserRegisterSessionPreferenceClient(
                LocalFactory.createPreference()
            )
        }
    }
}