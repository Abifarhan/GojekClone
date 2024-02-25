package com.ourproject.infrastructure.session

import android.content.SharedPreferences
import com.ourproject.session.usecase.LocalKey
import com.ourproject.session.usecase.LoginPreferenceClient

class UserLoginSessionPreferenceClient(private val preferences: SharedPreferences):
    LoginPreferenceClient {


    override fun saveSessionEmail(email: String) {

        val edit = preferences.edit()
        edit.putString(LocalKey.EMAIL_SESSION,email)
        edit.apply()
    }

    override fun getSessionEmail(): String {
        return preferences.getString(LocalKey.EMAIL_SESSION, "") ?: ""
    }


}