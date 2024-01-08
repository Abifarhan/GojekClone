package com.ourproject.register_cache.usecase

import com.ourproject.register_domain.usecase.UserSessionUseCase


class LocalSessionInsertUseCase(private val preferenceClient: PreferenceClient):
    UserSessionUseCase {

    override fun insertUserSession(email: String) {
        preferenceClient.saveSessionEmail(email)
    }


}