package com.ourproject.gojekclone.ui.framework

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object LocalFactory {

    lateinit var application: Application

    fun createPreference(): SharedPreferences = application.getSharedPreferences("email_preferences", Context.MODE_PRIVATE)
}