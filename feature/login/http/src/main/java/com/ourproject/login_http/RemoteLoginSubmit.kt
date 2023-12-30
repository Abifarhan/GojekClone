package com.ourproject.login_http

import HttpClientResult
import SubmitResult
import com.ourproject.ConnectivityException
import com.ourproject.InternalServerErrorException
import com.ourproject.InvalidDataException
import com.ourproject.NotFoundExceptionException
import com.ourproject.login_domain.LoginSubmit
import com.ourproject.login_domain.LoginSubmitEntity
import com.ourproject.login_domain.UserDomain
import com.ourproject.login_http.entity.LoginSubmitResultEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteLoginSubmit constructor(
    private val loginHttpClient: LoginHttpClient
) : LoginSubmit {
    override fun login(loginSubmitDto: LoginSubmitEntity): Flow<SubmitResult<UserDomain>> {

        return flow {

            val mapper = LoginSubmitDto(
                email = loginSubmitDto.email,
                password = loginSubmitDto.password
            )
            loginHttpClient.login(body = mapper).collect{ result ->
                when(result){
                    is HttpClientResult.Success -> {
                        val login = result.root

                        val dtoToLocal = UserDomain(
                            profilePhotoUrl = login.remoteLoginData.remoteUser.profilePhotoUrl,
                            address = login.remoteLoginData.remoteUser.address,
                            city = login.remoteLoginData.remoteUser.city,
                            roles = login.remoteLoginData.remoteUser.roles,
                            houseNumber = login.remoteLoginData.remoteUser.houseNumber,
                            createdAt = login.remoteLoginData.remoteUser.createdAt,
                            emailVerifiedAt = login.remoteLoginData.remoteUser.emailVerifiedAt,
                            currentTeamId = login.remoteLoginData.remoteUser.currentTeamId,
                            phoneNumber = login.remoteLoginData.remoteUser.phoneNumber,
                            updatedAt = login.remoteLoginData.remoteUser.updatedAt,
                            name = login.remoteLoginData.remoteUser.name,
                            id = login.remoteLoginData.remoteUser.id,
                            profilePhotoPath = login.remoteLoginData.remoteUser.profilePhotoPath,
                            email = login.remoteLoginData.remoteUser.email
                        )
                        emit(SubmitResult.Success(dtoToLocal))
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