package com.ourproject.gojekclone.ui.factories.local

import com.ourproject.gojekclone.ui.framework.LocalFactory
import com.ourproject.register_cache.usecase.LocalSession
import com.ourproject.register_cache.usecase.LocalSessionClient
import com.ourproject.register_cache.usecase.LocalSessionInsert
import com.ourproject.register_domain.local.RegisterSaveSession

class LocalSessionFactory {

    companion object{

        fun createLocalSessionInsert(): LocalSessionClient{
            return LocalSession(
                LocalFactory.createPreference()
            )
        }
    }
}