package com.ourproject.register_http.usecase.dto

import com.squareup.moshi.Json
data class RemoteRegisterDto(
    @Json(name="password")
    val password: String?,

    @Json(name="password_confirmation")
    val passwordConfirmation: String?,

    @Json(name="address")
    val address: String?,

    @Json(name="phoneNumber")
    val phoneNumber: String?,

    @Json(name="city")
    val city: String?,

    @Json(name="name")
    val name: String?,

    @Json(name="houseNumber")
    val houseNumber: String?,

    @Json(name="email")
    val email: String?
)
