package com.ourproject.gojekclone.ui.factories.local.register

import com.ourproject.core.framework.local.LocalFactory
import com.ourproject.infrastructure.session.UserSessionPreferenceClient
import com.ourproject.register_cache.usecase.PreferenceClient

class LocalRegisterSessionFactory {

    companion object{

        fun createLocalSessionInsert(): PreferenceClient{
            return UserSessionPreferenceClient(
                LocalFactory.createPreference()
            )
        }
    }
}