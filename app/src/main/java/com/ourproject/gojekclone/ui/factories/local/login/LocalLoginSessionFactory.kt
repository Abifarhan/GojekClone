package com.ourproject.gojekclone.ui.factories.local.login

import com.ourproject.gojekclone.ui.framework.LocalFactory
import com.ourproject.session.usecase.Preference
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