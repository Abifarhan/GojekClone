package com.ourproject.infrastructure.session

import android.content.SharedPreferences
import com.ourproject.register_cache.LocalKey
import com.ourproject.register_cache.RegisterPreferenceClient

class UserRegisterSessionPreferenceClient(private val preferences: SharedPreferences):
    RegisterPreferenceClient {

    override fun saveSessionEmail(email: String) {

        val edit = preferences.edit()
        edit.putString(LocalKey.EMAIL_SESSION, email)
        edit.apply()
    }

    override fun getSessionEmail(): String {
        return preferences.getString(LocalKey.EMAIL_SESSION, "") ?: ""
    }


}