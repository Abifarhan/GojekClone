package com.ourproject.login_domain

sealed class SubmitResult {
    data class Success(val data: UserEntity) : SubmitResult()

    data class Failure(val errorMessage: String) : SubmitResult()
}