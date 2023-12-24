package com.ourproject.register_module.datasource.http.usecase

import android.util.Log
import com.ourproject.register_module.datasource.http.ConnectivityException
import com.ourproject.register_module.datasource.http.HttpClientResult
import com.ourproject.register_module.datasource.http.InvalidDataException
import com.ourproject.register_module.datasource.http.RegisterFeedLoader
import com.ourproject.register_module.datasource.http.RegisterFeedHttpClient
import com.ourproject.register_module.datasource.http.RegisterFeedResult
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.domain.RegisterMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


class ConnectivityException : Exception()
class InvalidDataException : Exception()
class BadRequestException : Exception()
class NotFoundException : Exception()
class InternalServerErrorException : Exception()
class UnexpectedException : Exception()

class RemoteRegisterFeedLoader constructor(
    private val registerRetrofitHttpClient: RegisterFeedHttpClient
) : RegisterFeedLoader{
    override fun submit(userData: RegistrationEntity): Flow<RegisterFeedResult> {

        return flow {
            val mapper = RegisterMapper.mapRegistrationEntityToDto(userData)

            registerRetrofitHttpClient.submitRegister(mapper).collect {result ->
                when (result) {
                    is HttpClientResult.Success -> {
                        val dadta = result.root
                        val final = RegisterMapper.mapResponseDataDtoToEntity(dadta)
                        emit(RegisterFeedResult.Success(final))
                    }

                    is HttpClientResult.Failure -> {
                        when (result.throwable) {
                            is ConnectivityException -> {
                                emit(RegisterFeedResult.Failure(Connectivity()))
                            }

                            is InvalidDataException -> {
                                Log.d("loadCryptoFeed", "InvalidData")
                                emit(RegisterFeedResult.Failure(InvalidData()))
                            }

                            is BadRequestException -> {
                                emit(RegisterFeedResult.Failure(BadRequest()))
                            }

                            is NotFoundException -> {
                                emit(RegisterFeedResult.Failure(NotFound()))
                            }

                            is InternalServerErrorException -> {
                                emit(RegisterFeedResult.Failure(InternalServerError()))
                            }

                            is UnexpectedException -> {
                                emit(RegisterFeedResult.Failure(Unexpected()))
                            }
                        }
                    }

                    else -> {

                    }
                }
            }
        }

    }

}

class InvalidData : Throwable()
class Connectivity : Throwable()
class BadRequest : Exception()
class NotFound : Exception()
class InternalServerError : Exception()
class Unexpected : Exception()