package com.ourproject.infrastructure.remote

import com.ourproject.register_http.usecase.HttpClientResult
import com.ourproject.register_domain.ConnectivityException
import com.ourproject.register_domain.InternalServerErrorException
import com.ourproject.register_domain.InvalidDataException
import com.ourproject.register_domain.NotFoundExceptionException
import com.ourproject.register_domain.UnexpectedException
import com.ourproject.register_http.usecase.RegisterHttpClient
import com.ourproject.register_http.usecase.RegisterSubmitRequest
import com.ourproject.register_http.usecase.RegisterSubmitResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RegisterRetrofitClient constructor(
    private val registerService: RegisterService
) : RegisterHttpClient {
    override fun register(body: RegisterSubmitRequest): Flow<HttpClientResult> {
        return flow {
            try {
                val result = registerService.register(body.modelToDto()).remoteRegisterData
                emit(HttpClientResult.Success(result.dtoToModel()))
            } catch (t: Throwable) {
                val failureResult = when (t) {
                    is IOException -> HttpClientResult.Failure(ConnectivityException())
                    is HttpException -> {
                        when (t.code()) {
                            404 -> HttpClientResult.Failure(NotFoundExceptionException())
                            422 -> HttpClientResult.Failure(InvalidDataException())
                            500 -> HttpClientResult.Failure(InternalServerErrorException())
                            else -> HttpClientResult.Failure(UnexpectedException())
                        }
                    }
                    else -> HttpClientResult.Failure(UnexpectedException())
                }
                emit(failureResult)
            }
        }
    }


    private fun RegisterSubmitRequest.modelToDto() = RemoteRegisterRequest(
        password = this.password,
        passwordConfirmation = this.password_confirmation,
        address = this.address,
        phoneNumber = this.phoneNumber,
        city = this.city,
        name = this.name,
        houseNumber = this.houseNumber,
        email = this.email
    )


    private fun RemoteRegisterResponse.RemoteRegisterDto.dtoToModel() = RegisterSubmitResponse(
        profilePhotoUrl = this.user.profilePhotoUrl,
        address = this.user.address,
        city = this.user.city,
        roles = this.user.roles,
        houseNumber = this.user.houseNumber,
        createdAt = this.user.createdAt,
        emailVerifiedAt = this.user.emailVerifiedAt,
        currentTeamId = this.user.currentTeamId,
        phoneNumber = this.user.phoneNumber,
        updatedAt = this.user.updatedAt,
        name = this.user.name,
        id = this.user.id,
        profilePhotoPath = this.user.profilePhotoPath,
        email = this.user.email
    )
}