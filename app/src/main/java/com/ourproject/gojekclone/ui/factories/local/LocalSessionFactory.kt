package com.ourproject.gojekclone.ui.factories.local

import com.ourproject.gojekclone.ui.framework.LocalFactory
import com.ourproject.register_cache.usecase.Preference
import com.ourproject.register_cache.usecase.PreferenceClient

class LocalSessionFactory {

    companion object{

        fun createLocalSessionInsert(): PreferenceClient{
            return Preference(
                LocalFactory.createPreference()
            )
        }
    }
}