package com.ourproject.session.usecase

import com.ourproject.login_domain.UserSessionUseCase

class LocalSessionInsertUseCase(private val preferenceClient: PreferenceClient) :
 UserSessionUseCase{
    override fun insertUserSession(email: String) {
        preferenceClient.saveSessionEmail(email)
    }
}