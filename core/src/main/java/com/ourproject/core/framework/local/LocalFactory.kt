package com.ourproject.core.framework.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object LocalFactory {


    @Singleton
    @Provides
    fun createPreference(application: Application) : SharedPreferences{
        return application.getSharedPreferences("email_preferences", Context.MODE_PRIVATE)
    }
}