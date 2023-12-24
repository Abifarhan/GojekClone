package com.ourproject.register_module.datasource.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val gson: Gson = GsonBuilder()
    .setLenient()
    .create()
val baseUrl = "http://foodmarket-api.aryaaditiya.com/api/"
val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()