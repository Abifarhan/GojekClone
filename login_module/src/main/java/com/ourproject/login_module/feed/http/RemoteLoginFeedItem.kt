package com.ourproject.login_module.feed.http



data class LoginSubmitDto(
    val email: String,
    val password: String
)

data class LoginResultDto(
    val meta: Meta,
    val data: Data
)

data class Meta(
    val code: Int,
    val status: String,
    val message: String
)

data class Data(
    val access_token: String,
    val token_type: String,
    val user: User
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val email_verified_at: String?,

    val roles: String,

    val current_team_id: String?,

    val address: String,

    val houseNumber: String,

    val phoneNumber: String,

    val city: String,

    val created_at: Long,

    val updated_at: Long,

    val profile_photo_path: String?,

    val profile_photo_url: String
)

