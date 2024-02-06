package com.ourproject.session.usecase

interface LoginPreferenceClient {

    fun saveSessionEmail(userSessionSubmit : UserSessionSubmit)

    fun getSessionEmail(): String
}

class LocalKey{
    companion object{
        const val EMAIL_SESSION = "EMAIL_SESSION"
    }
}