package com.ourproject.login_http

import HttpClientResult
import SubmitResult
import com.ourproject.ConnectivityException
import com.ourproject.InternalServerErrorException
import com.ourproject.InvalidDataException
import com.ourproject.NotFoundExceptionException
import com.ourproject.login_domain.LoginSubmit
import com.ourproject.login_domain.LoginSubmitEntity
import com.ourproject.login_domain.LoginSubmitResultEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteLoginSubmit constructor(
    private val loginHttpClient: LoginHttpClient
) : LoginSubmit {
    override fun login(loginSubmitDto: LoginSubmitEntity): Flow<SubmitResult<LoginSubmitResultEntity>> {

        return flow {
            val body = LoginMapper.mapEntityToDto(loginSubmitDto)
            loginHttpClient.login(body = body).collect{ result ->
                when(result){
                    is HttpClientResult.Success -> {
                        val login = result.root
                        emit(SubmitResult.Success(LoginMapper.mapDtoToEntity(login.remoteLoginData)))
                    }

                    is HttpClientResult.Failure -> {
                        when(result.throwable){
                            is InvalidDataException -> {
                                emit(SubmitResult.Failure("Invalid Data"))
                            }
                            is ConnectivityException -> {
                                emit(SubmitResult.Failure("Connectivity"))
                            }
                            is NotFoundExceptionException -> {
                                emit(SubmitResult.Failure("Not Found"))
                            }
                            is InternalServerErrorException -> {
                                emit(SubmitResult.Failure("Internal Server Error"))
                            }
                        }
                    }
                    else-> {

                    }
                }
            }

        }
    }


}