package com.ourproject.register_cache.usecase

import com.ourproject.session_user.domain.UserSessionUseCase

class LocalSessionInsertUseCase(private val preferenceClient: PreferenceClient):
    UserSessionUseCase {

    override fun insertUserSession(email: String) {
        preferenceClient.saveSessionEmail(email)
    }


}