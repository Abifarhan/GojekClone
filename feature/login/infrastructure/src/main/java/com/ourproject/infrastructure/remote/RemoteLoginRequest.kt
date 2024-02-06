package com.ourproject.infrastructure.remote

import com.squareup.moshi.Json

data class RemoteLoginRequest(
    @Json(name="password")
    val password: String? = null,

    @Json(name="email")
    val email: String? = null
)
