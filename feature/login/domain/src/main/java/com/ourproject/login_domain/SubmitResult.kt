package com.ourproject.login_domain

sealed class SubmitResult {
    data class Success(val data: UserDomain) : SubmitResult()

    data class Failure(val errorMessage: String) : SubmitResult()
}