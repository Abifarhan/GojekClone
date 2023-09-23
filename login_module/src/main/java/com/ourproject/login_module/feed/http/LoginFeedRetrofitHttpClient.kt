package com.ourproject.login_module.feed.http

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginFeedRetrofitHttpClient constructor(
    private val loginFeedService: LoginFeedService
) : LoginFeedHttpClient {
    override fun submitLogin(loginSubmitDto: LoginSubmitDto): Flow<HttpClientResult> {
        return flow {
            try {
                val response = loginFeedService.submit(loginSubmitDto)

                if (response.isSuccessful) {
                    val responseData = response.body()
                    Log.d("TAG", "submitLogin: kita cek error sekarang ")

                    if (responseData != null) {
                        if (responseData.meta?.code == 200) {
                            emit(HttpClientResult.Success(responseData))
                        } else {
                            emit(HttpClientResult.Failure(Exception("Error: ${responseData?.meta?.message}")))
                        }
                    } else {
                        emit(HttpClientResult.Failure(Exception("Response Body is null")))
                    }
                } else {
                    emit(HttpClientResult.Failure(Exception("HTTP Error: ${response.code()}")))
                }
            } catch (t: Throwable) {
                emit(HttpClientResult.Failure(t))
            }
        }.flowOn(Dispatchers.IO)
    }

}