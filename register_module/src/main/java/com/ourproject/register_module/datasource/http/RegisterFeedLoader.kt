package com.ourproject.register_module.datasource.http

import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.datasource.http.dto.ResponseDataDto
import kotlinx.coroutines.flow.Flow


sealed class HttpRegisterClientResult {
    data class Success(val root : ResponseDataDto): HttpRegisterClientResult()

    data class Failure(val throwable: Throwable): HttpRegisterClientResult()
}

class InvalidDataException : Throwable()
class ConnectivityException : Throwable()
interface RegisterFeedLoader {

    fun submit(userData : RegistrationEntity): Flow<HttpClientResult>
}