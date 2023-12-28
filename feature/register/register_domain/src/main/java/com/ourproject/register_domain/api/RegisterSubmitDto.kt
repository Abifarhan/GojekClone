package com.ourproject.register_domain.api

import com.squareup.moshi.Json

data class RegisterSubmitDto(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String,
    val address: String,
    val city: String,
    val houseNumber: String,
    val phoneNumber: String
)


data class RemoteRegisterResponseDto(
    @Json(name="data")
    val remoteRegisterData: RemoteRegisterDto,

    @Json(name="meta")
    val meta: MetaDto
){

    data class RemoteRegisterDto(
        @Json(name="access_token")
        val accessToken: String,

        @Json(name="token_type")
        val tokenType: String,

        @Json(name="user")
        val user: UserDto
    )

    data class MetaDto(
        @Json(name="code")
        val code: Int,

        @Json(name="message")
        val message: String,

        @Json(name="status")
        val status: String
    )


    data class UserDto(
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
}