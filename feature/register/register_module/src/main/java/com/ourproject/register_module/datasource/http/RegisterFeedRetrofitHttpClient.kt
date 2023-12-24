package com.ourproject.register_module.datasource.http

import android.util.Log
import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.usecase.BadRequestException
import com.ourproject.register_module.datasource.http.usecase.InvalidData
import com.ourproject.register_module.datasource.http.usecase.NotFoundException
import com.ourproject.register_module.datasource.http.usecase.UnexpectedException
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
                        if (t.code() == 400) {
                            emit(HttpClientResult.Failure(BadRequestException()))
                        }

                        if (t.code() == 404) {
                            emit(HttpClientResult.Failure(NotFoundException()))
                        }
                    }

                    else -> {
                        emit(HttpClientResult.Failure(UnexpectedException()))
                    }
                }

            }
        }.flowOn(Dispatchers.IO)
    }


}