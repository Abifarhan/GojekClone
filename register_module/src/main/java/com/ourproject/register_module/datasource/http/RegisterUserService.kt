package com.ourproject.register_module.datasource.http

import com.ourproject.register_module.datasource.http.dto.RegistrationData
import com.ourproject.register_module.datasource.http.dto.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

    interface RegisterUserService {
        @POST("register")
        fun registerUser(@Body registrationData: RegistrationData): Call<ResponseData>
    }
