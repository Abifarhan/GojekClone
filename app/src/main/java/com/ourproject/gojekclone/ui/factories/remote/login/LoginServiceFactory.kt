package com.ourproject.gojekclone.ui.factories.remote.login

import com.ourproject.core.framework.remote.HttpFactory
import com.ourproject.infrastructure.remote.LoginService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class LoginServiceFactory {

    @Provides
    fun createLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }
}