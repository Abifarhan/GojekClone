package com.ourproject.register_domain

sealed class SubmitResult{
    data class Success(val data: UserDataDomain) : SubmitResult()

    data class Failure(val errorMessage: String) : SubmitResult()
}