package com.ourproject.gojekclone.ui.factories.local.register

import com.ourproject.register_cache.usecase.Preference
import com.ourproject.register_cache.usecase.PreferenceClient

class LocalRegisterSessionFactory {

    companion object{

        fun createLocalSessionInsert(): PreferenceClient{
            return Preference(
                com.ourproject.core.framework.local.LocalFactory.createPreference()
            )
        }
    }
}