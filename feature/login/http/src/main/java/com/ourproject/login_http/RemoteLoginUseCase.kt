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
    override fun login(loginSubmit: LoginSubmitDomain): Flow<SubmitResult> {

        return flow {
            loginHttpClient.login(body = loginSubmit.domainToModels()).collect{ result ->
                when(result){
                    is HttpClientResult.Success -> {
                        emit(SubmitResult.Success(result.root.modelToDomain()))
                    }
                    is HttpClientResult.Failure -> {
                        val failureMessage = when (result.throwable) {
                            is InvalidDataException -> "Invalid Data"
                            is ConnectivityException -> "Connectivity"
                            is NotFoundExceptionException -> "Not Found"
                            is InternalServerErrorException -> "Internal Server Error"
                            else -> "Unknown Error"
                        }
                        emit(SubmitResult.Failure(failureMessage))
                    }

                }
            }

        }
    }

    private fun LoginSubmitDomain.domainToModels(): LoginSubmitRequest {
        return LoginSubmitRequest(
            email = this.email,
            password = this.password
        )
    }

    private fun  LoginSubmitResult.modelToDomain(): UserDataDomain {
        return UserDataDomain(
            profilePhotoUrl = this.profilePhotoUrl,
            address = this.address,
            city = this.city,
            roles = this.roles,
            houseNumber = this.houseNumber,
            createdAt = this.createdAt,
            emailVerifiedAt = this.emailVerifiedAt,
            currentTeamId = this.currentTeamId,
            phoneNumber = this.phoneNumber,
            updatedAt = this.updatedAt,
            name = this.name,
            id = this.id,
            profilePhotoPath = this.profilePhotoPath,
            email = this.email
        )
    }

}