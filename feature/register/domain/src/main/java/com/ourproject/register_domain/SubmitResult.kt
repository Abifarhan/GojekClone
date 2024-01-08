package com.ourproject.register_domain

sealed class SubmitResult<T> {
    data class Success<T>(val data: T) : SubmitResult<T>()

    data class Failure<T>(val errorMessage: String) : SubmitResult<T>()
}