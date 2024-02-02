package com.ourproject.gojekclone.ui.factories.local.login

import com.ourproject.core.framework.local.LocalFactory
import com.ourproject.session.usecase.infrastructure.Preference
import com.ourproject.session.usecase.PreferenceClient

class LocalLoginSessionFactory {

    companion object{

        fun createLocalSessionInsert(): PreferenceClient {
            return Preference(
                LocalFactory.createPreference()
            )
        }
    }
}