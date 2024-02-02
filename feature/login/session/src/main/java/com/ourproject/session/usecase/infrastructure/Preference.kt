package com.ourproject.session.usecase.infrastructure

import android.content.SharedPreferences
import com.ourproject.session.usecase.LocalKey
import com.ourproject.session.usecase.PreferenceClient

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