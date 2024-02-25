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
                val loginResponse = loginService.login(RemoteLoginRequest(password = body.password, email = body.email))
                emit(HttpClientResult.Success(loginResponse.remoteLoginData.toModels()))
            } catch (throwable: Throwable){
                val result = when (throwable) {
                    is IOException -> HttpClientResult.Failure(ConnectivityException())
                    is HttpException -> {
                        when (throwable.code()) {
                            404 -> HttpClientResult.Failure(NotFoundExceptionException())
                            422 -> HttpClientResult.Failure(InvalidDataException())
                            500 -> HttpClientResult.Failure(InternalServerErrorException())
                            else -> HttpClientResult.Failure(InvalidDataException())
                        }
                    }
                    else -> HttpClientResult.Failure(InvalidDataException())
                }

                emit(result)
            }
        }
    }

    private fun RemoteLoginData.toModels(): LoginSubmitResult {
        return LoginSubmitResult(
            profilePhotoUrl = this.remoteUser.profilePhotoUrl,
            address = this.remoteUser.address,
            city = this.remoteUser.city,
            roles = this.remoteUser.roles,
            houseNumber = this.remoteUser.houseNumber,
            createdAt = this.remoteUser.createdAt,
            emailVerifiedAt = this.remoteUser.emailVerifiedAt,
            currentTeamId = this.remoteUser.currentTeamId,
            phoneNumber = this.remoteUser.phoneNumber,
            updatedAt = this.remoteUser.updatedAt,
            name = this.remoteUser.name,
            id = this.remoteUser.id,
            profilePhotoPath = this.remoteUser.profilePhotoPath,
            email = this.remoteUser.email
        )
    }

}