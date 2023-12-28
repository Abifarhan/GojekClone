package com.ourproject.gojekclone.ui.factories.local

import com.ourproject.register_cache.usecase.LocalSessionClient
import com.ourproject.register_cache.usecase.LocalSessionInsert
import com.ourproject.register_domain.local.RegisterSaveSession
import com.ourproject.session_user.PreferenceInsert

class LocalSessionInsertFactory {

    companion object{
        fun createLocalSession(): RegisterSaveSession{
            return LocalSessionInsert(
                LocalSessionFactory.createLocalSessionInsert()
            )
        }
    }
}