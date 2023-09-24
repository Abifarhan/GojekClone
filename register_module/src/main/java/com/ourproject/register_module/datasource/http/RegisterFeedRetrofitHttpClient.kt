package com.ourproject.register_module.datasource.http

import android.util.Log
import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.usecase.InvalidData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class RegisterFeedRetrofitHttpClient constructor(
    private val registerUserService: RegisterUserService
) : RegisterFeedHttpClient {

    override fun submitRegister(submit: RegistrationDto): Flow<HttpClientResult> {
        return flow {
            try {
                Log.d("TAG", "submit: submit: this part executed 1")
                emit(HttpClientResult.Success(registerUserService.registerUser(submit)))
            } catch (t: Throwable) {

                when (t) {
                    is IOException -> {
                        emit(HttpClientResult.Failure(ConnectivityException()))
                    }
                    is HttpException -> {
                        if (t.code() == 422) {
                            emit(HttpClientResult.Failure(InvalidDataException()))
                        }
                    }
                }
                emit(HttpClientResult.Failure(t))
            }
        }.flowOn(Dispatchers.IO)
    }


}