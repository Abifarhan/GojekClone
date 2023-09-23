package com.ourproject.register_module.datasource.http

import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.dto.ResponseDataDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

    interface RegisterUserService {
        @POST("register")
        fun registerUser(@Body registrationData: RegistrationDto): Call<ResponseDataDto>
    }
