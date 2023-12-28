package com.ourproject.login_http

import com.ourproject.login_domain.Meta
import com.squareup.moshi.Json

class LoginResultDto(
    @Json(name="data")
    val remoteLoginData: RemoteLoginData,

    @Json(name="meta")
    val meta: Meta
)

data class RemoteLoginData(

    @Json(name="access_token")
    val accessToken: String,

    @Json(name="token_type")
    val tokenType: String,

    @Json(name="user")
    val remoteUser: RemoteUser
)

data class Meta(

    @Json(name="code")
    val code: Int,

    @Json(name="message")
    val message: String,

    @Json(name="status")
    val status: String
)

data class RemoteUser(

    @Json(name="profile_photo_url")
    val profilePhotoUrl: String,

    @Json(name="address")
    val address: String,

    @Json(name="city")
    val city: String,

    @Json(name="roles")
    val roles: String,

    @Json(name="houseNumber")
    val houseNumber: String,

    @Json(name="created_at")
    val createdAt: Long,

    @Json(name="email_verified_at")
    val emailVerifiedAt: Any? = null,

    @Json(name="current_team_id")
    val currentTeamId: Any? = null,

    @Json(name="phoneNumber")
    val phoneNumber: String,

    @Json(name="updated_at")
    val updatedAt: Long,

    @Json(name="name")
    val name: String,

    @Json(name="id")
    val id: Int,

    @Json(name="profile_photo_path")
    val profilePhotoPath: Any? = null,

    @Json(name="email")
    val email: String
)