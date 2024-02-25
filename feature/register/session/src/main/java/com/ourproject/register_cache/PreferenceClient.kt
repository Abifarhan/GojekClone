package com.ourproject.register_cache

interface RegisterPreferenceClient {

    fun saveSessionEmail(email: String)

    fun getSessionEmail(): String
}


class LocalKey{
    companion object{
        const val EMAIL_SESSION = "EMAIL_SESSION"
    }
}