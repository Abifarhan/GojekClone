package com.ourproject.infrastructure.session

import android.content.SharedPreferences
import com.ourproject.session.usecase.LocalKey
import com.ourproject.session.usecase.LoginPreferenceClient
import com.ourproject.session.usecase.UserSessionSubmit
import javax.inject.Inject

class UserLoginSessionPreferenceClient @Inject constructor(private val preferences: SharedPreferences):
    LoginPreferenceClient {


    override fun saveSessionEmail(userSessionSubmit: UserSessionSubmit) {
        val sessionToInfrastructure = LoginLocalUserSessionSubmit(
            email = userSessionSubmit.email
        )
        val edit = preferences.edit()
        edit.putString(LocalKey.EMAIL_SESSION,sessionToInfrastructure.email)
        edit.apply()
    }

    override fun getSessionEmail(): String {
        return preferences.getString(LocalKey.EMAIL_SESSION, "") ?: ""
    }


}