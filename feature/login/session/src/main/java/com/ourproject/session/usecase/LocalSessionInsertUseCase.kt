package com.ourproject.session.usecase

import com.ourproject.login_domain.UserDataDomain
import com.ourproject.login_domain.UserSessionUseCase

class LocalSessionInsertUseCase(private val preferenceClient: LoginPreferenceClient) :
 UserSessionUseCase{


    override fun insertUserSession(data: UserDataDomain) {
        preferenceClient.saveSessionEmail(data.email)
    }
}