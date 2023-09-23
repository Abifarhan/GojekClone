package com.ourproject.register_module.datasource.http

import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.dto.ResponseDataDto
import kotlinx.coroutines.flow.Flow


sealed class HttpClientResult{
    data class Success(val root: ResponseDataDto): HttpClientResult()

    data class Failure(val throwable: Throwable) : HttpClientResult()
}
interface RegisterFeedHttpClient {

    fun submitRegister(submit: RegistrationDto) : Flow<HttpClientResult>
}