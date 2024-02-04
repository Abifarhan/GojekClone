package com.ourproject.core.framework.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
object LocalFactory {


    lateinit var application: Application

   @Singleton
   @Provides
   fun createPreference(): SharedPreferences = application.getSharedPreferences("email_preferences", Context.MODE_PRIVATE)
}