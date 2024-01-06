package com.ourproject.gojekclone.ui.factories.local

import com.ourproject.register_cache.usecase.LocalSessionInsertUseCase
import com.ourproject.session_user.domain.UserSessionUseCase

class LocalSessionInsertFactory {

    companion object{
        fun createLocalSession(): UserSessionUseCase {
            return LocalSessionInsertUseCase(
                LocalSessionFactory.createLocalSessionInsert()
            )
        }
    }
}