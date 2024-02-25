package com.ourproject.register_cache

import com.ourproject.register_domain.UserDataDomain
import com.ourproject.register_domain.UserSessionUseCase


class LocalSessionInsertUseCase(private val preferenceClient: RegisterPreferenceClient):
    UserSessionUseCase {


    override fun insertUserSession(userData: UserDataDomain) {
        preferenceClient.saveSessionEmail(userData.email)
    }


}