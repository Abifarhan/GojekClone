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
        return flow{
            try {


                val execution = registerService.register(
                    registerBody = RemoteRegisterRequest(
                        password = body.password,
                        passwordConfirmation = body.password_confirmation,
                        address = body.address,
                        phoneNumber = body.phoneNumber,
                        city = body.city,
                        name = body.name,
                        houseNumber = body.houseNumber,
                        email = body.email
                    )
                )

                val result = RegisterSubmitResponse(
                    profilePhotoUrl = execution.remoteRegisterData.user.profilePhotoUrl,
                    address = execution.remoteRegisterData.user.address,
                    city = execution.remoteRegisterData.user.city,
                    roles = execution.remoteRegisterData.user.roles,
                    houseNumber = execution.remoteRegisterData.user.houseNumber,
                    createdAt = execution.remoteRegisterData.user.createdAt,
                    emailVerifiedAt = execution.remoteRegisterData.user.emailVerifiedAt,
                    currentTeamId = execution.remoteRegisterData.user.currentTeamId,
                    phoneNumber = execution.remoteRegisterData.user.phoneNumber,
                    updatedAt = execution.remoteRegisterData.user.updatedAt,
                    name = execution.remoteRegisterData.user.name,
                    id = execution.remoteRegisterData.user.id,
                    profilePhotoPath = execution.remoteRegisterData.user.profilePhotoPath,
                    email = execution.remoteRegisterData.user.email
                )
                emit(HttpClientResult.Success(result))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> {
                        emit(HttpClientResult.Failure(ConnectivityException()))
                    }
                    is HttpException -> {
                        when(t.code()){
                            404 -> emit(HttpClientResult.Failure(NotFoundExceptionException()))
                            422 -> emit(HttpClientResult.Failure(InvalidDataException()))
                            500 -> emit(HttpClientResult.Failure(InternalServerErrorException()))
                        }
                    }
                    else -> {
                        emit(HttpClientResult.Failure(UnexpectedException()))
                    }
                }
            }
        }
    }


}