package com.ourproject.infrastructure.remote

import com.squareup.moshi.Json

data class RemoteRegisterResponse(
    @Json(name="data")
    val remoteRegisterData: RemoteRegisterDto = RemoteRegisterDto.DEFAULT,

    @Json(name="meta")
    val meta: MetaDto = MetaDto.DEFAULT
) {

    data class RemoteRegisterDto(
        @Json(name="access_token")
        val accessToken: String = "",

        @Json(name="token_type")
        val tokenType: String = "",

        @Json(name="user")
        val user: UserDto = UserDto.DEFAULT
    ) {
        companion object {
            val DEFAULT = RemoteRegisterDto(
                accessToken = "default_access_token",
                tokenType = "default_token_type",
                user = UserDto.DEFAULT
            )
        }
    }

    data class MetaDto(
        @Json(name="code")
        val code: Int = 0,

        @Json(name="message")
        val message: String = "",

        @Json(name="status")
        val status: String = ""
    ) {
        companion object {
            val DEFAULT = MetaDto(
                code = 0,
                message = "default_message",
                status = "default_status"
            )
        }
    }

    data class UserDto(
        @Json(name="profile_photo_url")
        val profilePhotoUrl: String? = null,

        @Json(name="address")
        val address: String = "",

        @Json(name="city")
        val city: String = "",

        @Json(name="roles")
        val roles: String = "",

        @Json(name="houseNumber")
        val houseNumber: String = "",

        @Json(name="created_at")
        val createdAt: Long = 0,

        @Json(name="email_verified_at")
        val emailVerifiedAt: Any? = null,

        @Json(name="current_team_id")
        val currentTeamId: Any? = null,

        @Json(name="phoneNumber")
        val phoneNumber: String = "",

        @Json(name="updated_at")
        val updatedAt: Long = 0,

        @Json(name="name")
        val name: String = "",

        @Json(name="id")
        val id: Int = 0,

        @Json(name="profile_photo_path")
        val profilePhotoPath: Any? = null,

        @Json(name="email")
        val email: String = ""
    ) {
        companion object {
            val DEFAULT = UserDto(
                id = 12344,
                name = "Default Name",
                email = "default@example.com",
                address = "Default Address",
                houseNumber = "123",
                phoneNumber = "555-1234",
                city = "Default City",
                profilePhotoUrl = "default_url"
            )
        }
    }
}