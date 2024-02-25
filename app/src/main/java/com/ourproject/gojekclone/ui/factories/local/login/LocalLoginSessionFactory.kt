package com.ourproject.gojekclone.ui.factories.local.login

import com.ourproject.infrastructure.session.UserLoginSessionPreferenceClient
import com.ourproject.session.usecase.LoginPreferenceClient
import dagger.Binds
import dagger.Module

@Module
abstract class LocalLoginSessionFactory {

    @Binds
    abstract fun createLocalSessionInsert(userLoginSessionPreferenceClient: UserLoginSessionPreferenceClient) : LoginPreferenceClient
}