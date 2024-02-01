package com.ourproject.gojekclone.ui.factories.local.register

import com.ourproject.register_cache.usecase.UserSessionPreferenceClient
import com.ourproject.register_cache.usecase.PreferenceClient

class LocalRegisterSessionFactory {

    companion object{

        fun createLocalSessionInsert(): PreferenceClient{
            return UserSessionPreferenceClient(
                com.ourproject.core.framework.local.LocalFactory.createPreference()
            )
        }
    }
}