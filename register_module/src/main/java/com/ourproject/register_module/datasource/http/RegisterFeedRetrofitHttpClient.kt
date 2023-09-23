package com.ourproject.register_module.datasource.http

import android.util.Log
import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterFeedRetrofitHttpClient constructor(
    private val registerUserService: RegisterUserService
) : RegisterFeedHttpClient {

    override fun submitRegister(submit: RegistrationDto): Flow<HttpClientResult> {
        return flow {
            try {
                Log.d("TAG", "submit: submit: this part executed 1")
                val call = registerUserService.registerUser(submit)
                val response = call.execute()

                if (response.isSuccessful) {
                    val responseData = response.body()
                    Log.d("TAG", "submit: submit: this part executed 2")
                    if (responseData != null) {
                        if (responseData.meta?.code == 200) {
                            Log.d("TAG", "submit: submit: this part executed 2 $responseData")
                            emit(HttpClientResult.Success(responseData))
                        } else {
                            emit(HttpClientResult.Failure(Exception("Error: ${responseData.meta?.message}")))
                        }
                    } else {
                        emit(HttpClientResult.Failure(Exception("Response body is null")))
                    }
                } else {
                    emit(HttpClientResult.Failure(Exception("HTTP error: ${response.code()}")))
                }
            } catch (t: Throwable) {
                Log.d("TAG", "submit: submit: this part executed 3")
                emit(HttpClientResult.Failure(t))
            }
        }.flowOn(Dispatchers.IO)
    }


}