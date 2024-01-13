package com.ourproject.gojekclone.ui.factories.local.login

import com.ourproject.login_domain.UserSessionUseCase
import com.ourproject.session.usecase.LocalSessionInsertUseCase

class LocalLoginSessionInsertFactory {

    companion object{
        fun createLocalSession(): UserSessionUseCase {
            return LocalSessionInsertUseCase(
                LocalLoginSessionFactory.createLocalSessionInsert()
            )
        }
    }
}