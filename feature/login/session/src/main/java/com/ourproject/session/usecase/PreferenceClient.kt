package com.ourproject.session.usecase

interface PreferenceClient {

    fun saveSessionEmail(email:String)

    fun getSessionEmail(): String
}

class LocalKey{
    companion object{
        const val EMAIL_SESSION = "EMAIL_SESSION"
    }
}