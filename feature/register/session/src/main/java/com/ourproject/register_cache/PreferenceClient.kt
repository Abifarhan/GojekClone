package com.ourproject.register_cache

interface PreferenceClient {

    fun saveSessionEmail(userData:UserSessionSubmit)

    fun getSessionEmail(): String
}


class LocalKey{
    companion object{
        const val EMAIL_SESSION = "EMAIL_SESSION"
    }
}