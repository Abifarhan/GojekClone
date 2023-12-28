package com.ourproject.register_cache.usecase

import com.ourproject.register_domain.local.RegisterSaveSession
import com.ourproject.session_user.PreferenceInsert

class LocalSessionInsert(private val localSessionClient: LocalSessionClient):
    RegisterSaveSession {

    override fun insertUserSession(email: String) {
        localSessionClient.saveSessionEmail(email)
    }


}