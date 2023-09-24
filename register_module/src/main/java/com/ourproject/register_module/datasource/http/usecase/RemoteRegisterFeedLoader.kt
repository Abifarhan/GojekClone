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
                        }
                    }
                }
            }
        }

    }

}

class InvalidData : Throwable()
class Connectivity : Throwable()