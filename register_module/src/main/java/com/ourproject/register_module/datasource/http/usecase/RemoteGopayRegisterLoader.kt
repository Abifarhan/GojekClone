package com.ourproject.register_module.datasource.http.usecase

import com.ourproject.register_module.datasource.http.GoPayRegisterLoader
import com.ourproject.register_module.datasource.http.HttpRegisterClientResult
import com.ourproject.register_module.datasource.http.RegisterFeedRetrofitHttpClient
import com.ourproject.register_module.datasource.http.dto.RegistrationData
import kotlinx.coroutines.flow.Flow

class RemoteGopayRegisterLoader constructor(
    private val registerRetrofitHttpClient: RegisterFeedRetrofitHttpClient
) : GoPayRegisterLoader{
    override fun submit(userData: RegistrationData): Flow<HttpRegisterClientResult> {
        return registerRetrofitHttpClient.submit(userData)



//        return flow {
//            try {
//                // Perform the registration using Retrofit or your HTTP client
//                val response = registerRetrofitHttpClient.submit(userData)
//
//                if (response.isSuccessful) {
//                    // Emit a success result with the response data
//                    emit(HttpRegisterClientResult.Success(response.body()))
//                } else {
//                    // Emit a failure result with the error message
//                    emit(HttpRegisterClientResult.Failure(Exception("Registration failed")))
//                }
//            } catch (e: Exception) {
//                // Handle exceptions, and emit a failure result with the exception
//                emit(HttpRegisterClientResult.Failure(e))
//            }
//        }.flowOn(Dispatchers.IO)
    }

}