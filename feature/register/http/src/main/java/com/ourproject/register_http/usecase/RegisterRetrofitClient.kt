package com.ourproject.register_http.usecase

import HttpClientResult
import com.ourproject.ConnectivityException
import com.ourproject.InternalServerErrorException
import com.ourproject.InvalidDataException
import com.ourproject.NotFoundExceptionException
import com.ourproject.UnexpectedException
import com.ourproject.register_http.usecase.dto.RegisterSubmitDto
import com.ourproject.register_http.usecase.dto.RemoteRegisterResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RegisterRetrofitClient constructor(
    private val registerService: RegisterService
) : RegisterHttpClient {
    override fun register(body: RegisterSubmitDto): Flow<HttpClientResult<RemoteRegisterResponseDto>> {
        return flow{
            try {
                emit(HttpClientResult.Success(registerService.register(body)))
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