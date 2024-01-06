package com.ourproject.login_http

import com.ourproject.session_user.HttpClientResult
import com.ourproject.session_user.ConnectivityException
import com.ourproject.session_user.InternalServerErrorException
import com.ourproject.session_user.InvalidDataException
import com.ourproject.session_user.NotFoundExceptionException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LoginRetrofitClient constructor(
    private val loginService: LoginService
) : LoginHttpClient {
    override fun login(body: LoginSubmitDto): Flow<HttpClientResult<LoginResultDto>> {
        return flow {
            try {
                emit(HttpClientResult.Success(loginService.login(body)))
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