package com.ourproject.register_http.usecase

import HttpClientResult
import SubmitResult
import android.util.Log
import com.ourproject.ConnectivityException
import com.ourproject.InternalServerErrorException
import com.ourproject.InvalidDataException
import com.ourproject.NotFoundExceptionException
import com.ourproject.UnexpectedException
import com.ourproject.register_domain.api.RegisterSubmit
import com.ourproject.register_domain.api.RegisterSubmitDto
import com.ourproject.register_domain.api.RemoteRegisterResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteRegisterSubmit(
    private val registerHttpClient: RegisterHttpClient
) : RegisterSubmit {
    override fun register(registerSubmitDto: RegisterSubmitDto): Flow<SubmitResult<RemoteRegisterResponseDto>> {
        return flow{
           registerHttpClient.register(body = registerSubmitDto).collect{ result ->
               when(result){
                   is HttpClientResult.Success -> {
                       val register = result.root
                       emit(SubmitResult.Success(register))
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

                   else -> {

                   }
               }
           }
        }
    }


}