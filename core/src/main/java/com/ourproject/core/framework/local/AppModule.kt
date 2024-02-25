package com.ourproject.core.framework.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ourproject.infrastructure.session.UserLoginSessionPreferenceClient
import com.ourproject.session.usecase.LoginPreferenceClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule() {

    @Binds
    abstract fun bindContext(application: Application) : Context

    @Binds
    abstract fun bindSharedPreference(appPreferenceImpl : UserLoginSessionPreferenceClient) : LoginPreferenceClient


}