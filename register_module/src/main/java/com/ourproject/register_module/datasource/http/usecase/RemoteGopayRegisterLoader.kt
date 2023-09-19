package com.ourproject.register_module.datasource.http.usecase

import android.util.Log
import com.ourproject.register_module.datasource.http.GoPayRegisterLoader
import com.ourproject.register_module.datasource.http.HttpRegisterClientResult
import com.ourproject.register_module.datasource.http.RegisterRetrofitHttpClient
import com.ourproject.register_module.datasource.http.dto.RegistrationData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class RemoteGopayRegisterLoader constructor(
    private val registerRetrofitHttpClient: RegisterRetrofitHttpClient
) : GoPayRegisterLoader{
    override fun submit(userData: RegistrationData): Flow<HttpRegisterClientResult> {
        return flow {
            Log.d("TAG", "Entering flow block")
            val result = registerRetrofitHttpClient.submit(userData)
            val firstSuccess = result.firstOrNull { it is HttpRegisterClientResult.Success }

            if (firstSuccess != null) {
                Log.d("TAG", "Emitting firstSuccess: $firstSuccess")
                emit(firstSuccess)
            } else {
                Log.d("TAG", "No successful result found, emitting failure")
                // No successful result found, emit a failure
                emit(HttpRegisterClientResult.Failure(Exception("No successful result")))
            }
        }.onEach { result ->
            when (result) {
                is HttpRegisterClientResult.Success -> {
                    Log.d("TAG", "HTTP request is successful: ${result.root}")
                }
                is HttpRegisterClientResult.Failure -> {
                    Log.e("TAG", "HTTP request failed: ${result.throwable.message}")
                }
            }
        }.flowOn(Dispatchers.IO)



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