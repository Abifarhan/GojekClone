package com.ourproject.login_module.feed.http

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginFeedService {

    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun submit(
        @Body loginSubmitDto: LoginSubmitDto
    ): retrofit2.Response<LoginResultDto>


}