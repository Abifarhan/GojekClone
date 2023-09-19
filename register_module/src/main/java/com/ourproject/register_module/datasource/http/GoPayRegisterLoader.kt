package com.ourproject.register_module.datasource.http

import com.ourproject.register_module.datasource.http.dto.RegistrationData
import com.ourproject.register_module.datasource.http.dto.ResponseData
import kotlinx.coroutines.flow.Flow


sealed class HttpRegisterClientResult {
    data class Success(val root : ResponseData): HttpRegisterClientResult()

    data class Failure(val throwable: Throwable): HttpRegisterClientResult()
}

class InvalidDataException : Throwable()
class ConnectivityException : Throwable()
interface GoPayRegisterLoader {

    fun submit(userData : RegistrationData): Flow<HttpRegisterClientResult>
}