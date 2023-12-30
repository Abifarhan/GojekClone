package com.ourproject.login_http

import com.squareup.moshi.Json

class LoginSubmitDto(
    @Json(name="password")
    val password: String? = null,

    @Json(name="email")
    val email: String? = null
)