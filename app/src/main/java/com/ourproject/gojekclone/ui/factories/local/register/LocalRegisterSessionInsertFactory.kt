package com.ourproject.gojekclone.ui.factories.local.register

import com.ourproject.register_cache.LocalSessionInsertUseCase
import com.ourproject.register_domain.UserSessionUseCase

class LocalRegisterSessionInsertFactory {

    companion object{
        fun createLocalSession(): UserSessionUseCase {
            return LocalSessionInsertUseCase(
                LocalRegisterSessionFactory.createLocalSessionInsert()
            )
        }
    }
}