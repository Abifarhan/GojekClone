package com.ourproject.register_module.datasource.http

import com.ourproject.register_module.datasource.http.dto.RegistrationData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

    interface ApiService {
        @POST("register")
        fun registerUser(@Body registrationData: RegistrationData): Call<ResponseData>
    }
