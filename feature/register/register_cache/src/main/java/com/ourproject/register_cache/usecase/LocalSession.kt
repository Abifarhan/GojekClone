package com.ourproject.register_cache.usecase

import android.content.SharedPreferences

class LocalSession(private val preferences: SharedPreferences): LocalSessionClient {
    override fun saveSessionEmail(email: String) {
        val edit = preferences.edit()
        edit.putString(LocalKey.EMAIL_SESSION, email)
        edit.apply()
    }

    override fun getSessionEmail(): String {
        return preferences.getString(LocalKey.EMAIL_SESSION, "") ?: ""
    }


}