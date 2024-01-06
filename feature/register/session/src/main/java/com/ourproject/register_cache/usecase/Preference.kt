package com.ourproject.register_cache.usecase

import android.content.SharedPreferences

class Preference(private val preferences: SharedPreferences): PreferenceClient {
    override fun saveSessionEmail(email: String) {
        val edit = preferences.edit()
        edit.putString(LocalKey.EMAIL_SESSION, email)
        edit.apply()
    }

    override fun getSessionEmail(): String {
        return preferences.getString(LocalKey.EMAIL_SESSION, "") ?: ""
    }


}