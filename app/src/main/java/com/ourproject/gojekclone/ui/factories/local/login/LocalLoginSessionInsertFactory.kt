package com.ourproject.gojekclone.ui.factories.local.login

import com.ourproject.login_domain.UserSessionUseCase
import com.ourproject.session.usecase.LocalSessionInsertUseCase
import dagger.Binds
import dagger.Module


@Module
abstract class LocalLoginSessionInsertFactory {

    @Binds
    abstract fun createLocalSessionUseCase(
        localSessionInsertUseCase: LocalSessionInsertUseCase
    ) : UserSessionUseCase
}