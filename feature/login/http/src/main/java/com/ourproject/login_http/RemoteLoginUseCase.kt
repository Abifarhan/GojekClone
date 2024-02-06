package com.ourproject.login_http


import com.ourproject.login_domain.ConnectivityException
import com.ourproject.login_domain.InternalServerErrorException
import com.ourproject.login_domain.InvalidDataException
import com.ourproject.login_domain.LoginUseCase
import com.ourproject.login_domain.LoginSubmitDomain
import com.ourproject.login_domain.NotFoundExceptionException
import com.ourproject.login_domain.SubmitResult
import com.ourproject.login_domain.UserDataDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteLoginUseCase constructor(
    private val loginHttpClient: LoginHttpClient
) : LoginUseCase {
    override fun login(loginSubmitDto: LoginSubmitDomain): Flow<SubmitResult> {

        return flow {

            val mapper = LoginSubmitRequest(
                email = loginSubmitDto.email,
                password = loginSubmitDto.password
            )
            loginHttpClient.login(body = mapper).collect{ result ->
                when(result){
                    is HttpClientResult.Success -> {
                        val login = result.root

                        val dtoToLocal = UserDataDomain(
                            profilePhotoUrl = login.profilePhotoUrl,
                            address = login.address,
                            city = login.city,
                            roles = login.roles,
                            houseNumber = login.houseNumber,
                            createdAt = login.createdAt,
                            emailVerifiedAt = login.emailVerifiedAt,
                            currentTeamId = login.currentTeamId,
                            phoneNumber = login.phoneNumber,
                            updatedAt = login.updatedAt,
                            name = login.name,
                            id = login.id,
                            profilePhotoPath = login.profilePhotoPath,
                            email = login.email
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

                }
            }

        }
    }


}