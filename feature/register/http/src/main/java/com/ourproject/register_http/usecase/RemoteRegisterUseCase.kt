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

            registerHttpClient.register(body = registerSubmit.remoteToInfrastructure()).collect{ result ->
                when(result){
                    is HttpClientResult.Success -> {
                        val resultData = result.root
                        emit(SubmitResult.Success(resultData.remoteToDomain()))
                    }

                    is HttpClientResult.Failure -> {
                        val failureResult = when (result.throwable) {
                            is InvalidDataException -> "Invalid Data"
                            is ConnectivityException -> "Connectivity"
                            is NotFoundExceptionException -> "Not Found"
                            is InternalServerErrorException -> "Internal Server Error"
                            is UnexpectedException -> "Something went wrong"
                            else -> "Unknown Error"
                        }

                        emit(SubmitResult.Failure(failureResult))
                    }
                }
            }
        }
    }


    private fun RegisterSubmitDomain.remoteToInfrastructure() = RegisterSubmitRequest(
        name = this.name,
        email = this.email,
        password = this.password,
        password_confirmation = this.password_confirmation,
        address = this.password_confirmation,
        city = this.city,
        houseNumber = this.houseNumber,
        phoneNumber = this.phoneNumber
    )

    private fun RegisterSubmitResponse.remoteToDomain() = UserDataDomain(
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