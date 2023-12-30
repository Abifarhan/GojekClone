package com.ourproject.register_http.usecase

import HttpClientResult
import SubmitResult
import com.ourproject.ConnectivityException
import com.ourproject.InternalServerErrorException
import com.ourproject.InvalidDataException
import com.ourproject.NotFoundExceptionException
import com.ourproject.UnexpectedException
import com.ourproject.register_domain.api.RegisterSubmit
import com.ourproject.register_domain.api.RegisterSubmitEntity
import com.ourproject.register_domain.local.UserEntity
import com.ourproject.register_http.usecase.dto.RegisterSubmitDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteRegisterSubmit(
    private val registerHttpClient: RegisterHttpClient
) : RegisterSubmit {

    override fun register(registerSubmit: RegisterSubmitEntity): Flow<SubmitResult<UserEntity>> {
        return flow{

            val mapEntityToDto = RegisterSubmitDto(
                name = registerSubmit.name,
                email = registerSubmit.email,
                password = registerSubmit.password,
                password_confirmation = registerSubmit.password_confirmation,
                address = registerSubmit.password_confirmation,
                city = registerSubmit.city,
                houseNumber = registerSubmit.houseNumber,
                phoneNumber = registerSubmit.phoneNumber
            )
            registerHttpClient.register(body = mapEntityToDto).collect{ result ->
                when(result){
                    is HttpClientResult.Success -> {
                        val register = result.root

                        val mapResultDtoToLocal = UserEntity(
                            profilePhotoUrl = register.remoteRegisterData.user.profilePhotoUrl,
                            address = register.remoteRegisterData.user.address,
                            city = register.remoteRegisterData.user.city,
                            roles = register.remoteRegisterData.user.roles,
                            houseNumber = register.remoteRegisterData.user.houseNumber,
                            createdAt = register.remoteRegisterData.user.createdAt,
                            emailVerifiedAt = register.remoteRegisterData.user.emailVerifiedAt,
                            currentTeamId = register.remoteRegisterData.user.currentTeamId,
                            phoneNumber = register.remoteRegisterData.user.phoneNumber,
                            updatedAt = register.remoteRegisterData.user.updatedAt,
                            name = register.remoteRegisterData.user.name,
                            id = register.remoteRegisterData.user.id,
                            profilePhotoPath = register.remoteRegisterData.user.profilePhotoPath,
                            email =register.remoteRegisterData.user.email
                        )
                        emit(SubmitResult.Success(mapResultDtoToLocal))
                    }

                    is HttpClientResult.Failure -> {
                        when (result.throwable) {
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
                            is UnexpectedException -> {
                                emit(SubmitResult.Failure("Something went wrong"))
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