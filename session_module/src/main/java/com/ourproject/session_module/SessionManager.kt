package com.ourproject.session_module

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME = "UserSession"
    private const val KEY_USER_EMAIL = "user_email"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserData(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USER_EMAIL, email)
        editor.apply()
    }

    fun retrieveUserData(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }
}