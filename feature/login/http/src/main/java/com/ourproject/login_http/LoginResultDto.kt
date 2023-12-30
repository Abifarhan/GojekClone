package com.ourproject.login_http

import com.squareup.moshi.Json

data class LoginResultDto(
    @Json(name="data")
    val remoteLoginData: RemoteLoginData = RemoteLoginData.DEFAULT,

    @Json(name="meta")
    val meta: Meta = Meta.DEFAULT
) {

    companion object {
        val DEFAULT = LoginResultDto(
            remoteLoginData = RemoteLoginData.DEFAULT,
            meta = Meta.DEFAULT
        )
    }
}

data class RemoteLoginData(
    @Json(name="access_token")
    val accessToken: String,

    @Json(name="token_type")
    val tokenType: String,

    @Json(name="user")
    val remoteUser: RemoteUser
) {
    companion object {
        val DEFAULT = RemoteLoginData(
            accessToken = "defaultAccessToken",
            tokenType = "defaultTokenType",
            remoteUser = RemoteUser.DEFAULT
        )
    }
}

data class Meta(
    @Json(name="code")
    val code: Int,

    @Json(name="message")
    val message: String,

    @Json(name="status")
    val status: String
) {
    companion object {
        val DEFAULT = Meta(
            code = 0,
            message = "defaultMessage",
            status = "defaultStatus"
        )
    }
}

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
) {
    companion object {
        val DEFAULT = RemoteUser(
            profilePhotoUrl = "defaultProfilePhotoUrl",
            address = "Default Address",
            city = "Default City",
            roles = "Default Roles",
            houseNumber = "123",
            createdAt = System.currentTimeMillis(),
            emailVerifiedAt = null,
            currentTeamId = null,
            phoneNumber = "555-1234",
            updatedAt = System.currentTimeMillis(),
            name = "Default Name",
            id = 0,
            profilePhotoPath = null,
            email = "default@example.com"
        )
    }
}
