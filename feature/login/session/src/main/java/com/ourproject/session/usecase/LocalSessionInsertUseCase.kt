package com.ourproject.session.usecase

import com.ourproject.login_domain.UserDataDomain
import com.ourproject.login_domain.UserSessionUseCase
import javax.inject.Inject

class LocalSessionInsertUseCase @Inject constructor(private val preferenceClient: LoginPreferenceClient) :
 UserSessionUseCase{

    override fun insertUserSession(data: UserDataDomain) {
        val mapper = UserSessionSubmit(
            email = data.email
        )
        preferenceClient.saveSessionEmail(mapper)
    }
}