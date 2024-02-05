package com.ourproject.register_domain

interface UserSessionUseCase{
    fun insertUserSession(userData: UserDataDomain)
}