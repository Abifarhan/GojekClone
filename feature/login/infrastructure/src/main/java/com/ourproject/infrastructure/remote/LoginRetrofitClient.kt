package com.ourproject.infrastructure.remote


import com.ourproject.login_domain.ConnectivityException
import com.ourproject.login_domain.InternalServerErrorException
import com.ourproject.login_domain.InvalidDataException
import com.ourproject.login_domain.NotFoundExceptionException
import com.ourproject.login_http.HttpClientResult
import com.ourproject.login_http.LoginHttpClient
import com.ourproject.login_http.LoginSubmitRequest
import com.ourproject.login_http.LoginSubmitResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LoginRetrofitClient constructor(
    private val loginService: LoginService
) : LoginHttpClient {
    override fun login(body: LoginSubmitRequest): Flow<HttpClientResult> {
        return flow {
            try {
                val execution = loginService.login(
                    loginBody = RemoteLoginRequest(
                        password = body.password,
                        email = body.email
                    )
                )

                val result = LoginSubmitResult(
                    profilePhotoUrl = execution.remoteLoginData.remoteUser.profilePhotoUrl,
                    address = execution.remoteLoginData.remoteUser.address,
                    city = execution.remoteLoginData.remoteUser.city,
                    roles = execution.remoteLoginData.remoteUser.roles,
                    houseNumber = execution.remoteLoginData.remoteUser.houseNumber,
                    createdAt = execution.remoteLoginData.remoteUser.createdAt,
                    emailVerifiedAt = execution.remoteLoginData.remoteUser.emailVerifiedAt,
                    currentTeamId = execution.remoteLoginData.remoteUser.currentTeamId,
                    phoneNumber = execution.remoteLoginData.remoteUser.phoneNumber,
                    updatedAt = execution.remoteLoginData.remoteUser.updatedAt,
                    name = execution.remoteLoginData.remoteUser.name,
                    id = execution.remoteLoginData.remoteUser.id,
                    profilePhotoPath = execution.remoteLoginData.remoteUser.profilePhotoPath,
                    email = execution.remoteLoginData.remoteUser.email
                )
                emit(HttpClientResult.Success(result))
            } catch (throwable: Throwable){
                when(throwable) {
                    is IOException -> {
                        emit(HttpClientResult.Failure(ConnectivityException()))
                    }
                    is HttpException -> {
                        when(throwable.code()){
                            404 -> emit(HttpClientResult.Failure(NotFoundExceptionException()))
                            422 -> emit(HttpClientResult.Failure(InvalidDataException()))
                            500 -> emit(HttpClientResult.Failure(InternalServerErrorException()))
                        }
                    }
                    else -> {
                        emit(HttpClientResult.Failure(InvalidDataException()))
                    }
                }
            }
        }
    }

}