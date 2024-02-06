package com.ourproject.login_http

import com.squareup.moshi.Json

class LoginSubmitRequest(
    val password: String? = null,

    val email: String? = null
)