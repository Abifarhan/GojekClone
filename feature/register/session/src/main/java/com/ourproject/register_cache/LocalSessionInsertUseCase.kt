package com.ourproject.register_cache

import com.ourproject.register_domain.UserDataDomain
import com.ourproject.register_domain.UserSessionUseCase


class LocalSessionInsertUseCase(private val preferenceClient: PreferenceClient):
    UserSessionUseCase {


    override fun insertUserSession(userData: UserDataDomain) {
        val userDomainToSession = UserSessionSubmit(
            email = userData.email
        )
        preferenceClient.saveSessionEmail(userDomainToSession)
    }


}