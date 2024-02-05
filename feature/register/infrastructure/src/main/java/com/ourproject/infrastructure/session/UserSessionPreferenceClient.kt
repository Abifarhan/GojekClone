package com.ourproject.infrastructure.session

import android.content.SharedPreferences
import com.ourproject.register_cache.usecase.LocalKey
import com.ourproject.register_cache.usecase.PreferenceClient

class UserSessionPreferenceClient(private val preferences: SharedPreferences): PreferenceClient {
    override fun saveSessionEmail(email: String) {
        val edit = preferences.edit()
        edit.putString(LocalKey.EMAIL_SESSION, email)
        edit.apply()
    }

    override fun getSessionEmail(): String {
        return preferences.getString(LocalKey.EMAIL_SESSION, "") ?: ""
    }


}