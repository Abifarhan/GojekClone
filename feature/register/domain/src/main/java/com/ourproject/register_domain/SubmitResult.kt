package com.ourproject.register_domain

import com.ourproject.register_domain.local.UserEntity

sealed class SubmitResult{
    data class Success(val data: UserEntity) : SubmitResult()

    data class Failure(val errorMessage: String) : SubmitResult()
}