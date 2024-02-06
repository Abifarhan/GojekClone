package com.ourproject.infrastructure.session

import android.content.SharedPreferences
import com.ourproject.register_cache.LocalKey
import com.ourproject.register_cache.RegisterPreferenceClient
import com.ourproject.register_cache.UserSessionSubmit

class UserRegisterSessionPreferenceClient(private val preferences: SharedPreferences):
    RegisterPreferenceClient {

    override fun saveSessionEmail(userData: UserSessionSubmit) {
        val sessionToInfrastructure = LocalUserSessionSubmit(
            email = userData.email
        )
        val edit = preferences.edit()
        edit.putString(LocalKey.EMAIL_SESSION, sessionToInfrastructure.email)
        edit.apply()
    }

    override fun getSessionEmail(): String {
        return preferences.getString(LocalKey.EMAIL_SESSION, "") ?: ""
    }


}