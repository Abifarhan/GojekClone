package com.ourproject.login_module.feed.http

data class LoginSubmitDto(
    val email: String = "",
    val password: String = ""
) {
    companion object {
        val DEFAULT = LoginSubmitDto(
            email = "default@gmail.com",
            password = "1234567890"
        )
    }
}

data class LoginResultDto(
    val meta: Meta = Meta.DEFAULT,
    val data: Data = Data.DEFAULT
) {
    companion object{
        val DEFAULT = LoginResultDto(
            meta = Meta.DEFAULT,
            data = Data.DEFAULT
        )
    }
}

data class Meta(
    val code: Int = 0,
    val status: String = "",
    val message: String = ""
) {
    companion object {
        val DEFAULT = Meta(
            code = 0,
            status = "Default Status",
            message = "Default Message"
        )
    }
}

data class Data(
    val access_token: String = "",
    val token_type: String = "",
    val user: User = User.DEFAULT
) {
    companion object {
        val DEFAULT = Data(
            access_token = "Default Token",
            token_type = "Default Type",
            user = User.DEFAULT
        )
    }
}

data class User(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val email_verified_at: String? = null,
    val roles: String = "",
    val current_team_id: String? = null,
    val address: String = "",
    val houseNumber: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val created_at: Long = 0,
    val updated_at: Long = 0,
    val profile_photo_path: String? = null,
    val profile_photo_url: String = ""
) {
    companion object {
        val DEFAULT = User(
            id = 12344,
            name = "Default Name",
            email = "default@example.com",
            address = "Default Address",
            houseNumber = "123",
            phoneNumber = "555-1234",
            city = "Default City",
            profile_photo_url = "default_url"
        )
    }
}
