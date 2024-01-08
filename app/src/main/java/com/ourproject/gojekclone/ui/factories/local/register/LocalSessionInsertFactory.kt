package com.ourproject.gojekclone.ui.factories.local.register

import com.ourproject.register_cache.usecase.LocalSessionInsertUseCase
import com.ourproject.register_domain.usecase.UserSessionUseCase

class LocalSessionInsertFactory {

    companion object{
        fun createLocalSession(): UserSessionUseCase {
            return LocalSessionInsertUseCase(
                LocalSessionFactory.createLocalSessionInsert()
            )
        }
    }
}