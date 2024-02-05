package com.ourproject.register_http.usecase


import com.ourproject.register_domain.ConnectivityException
import com.ourproject.register_domain.InternalServerErrorException
import com.ourproject.register_domain.InvalidDataException
import com.ourproject.register_domain.NotFoundExceptionException
import com.ourproject.register_domain.SubmitResult
import com.ourproject.register_domain.UnexpectedException
import com.ourproject.register_domain.RegisterUseCase
import com.ourproject.register_domain.RegisterSubmitDomain
import com.ourproject.register_domain.UserDataDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteRegisterUseCase(
    private val registerHttpClient: RegisterHttpClient
) : RegisterUseCase {

    override fun register(registerSubmit: RegisterSubmitDomain): Flow<SubmitResult> {
        return flow{

            val mapEntityToDto = RegisterSubmitRequest(
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

                        val mapResultDtoToLocal = UserDataDomain(
                            profilePhotoUrl = register.profilePhotoUrl,
                            address = register.address,
                            city = register.city,
                            roles = register.roles,
                            houseNumber = register.houseNumber,
                            createdAt = register.createdAt,
                            emailVerifiedAt = register.emailVerifiedAt,
                            currentTeamId = register.currentTeamId,
                            phoneNumber = register.phoneNumber,
                            updatedAt = register.updatedAt,
                            name = register.name,
                            id = register.id,
                            profilePhotoPath = register.profilePhotoPath,
                            email =register.email
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
                }
            }
        }
    }


}