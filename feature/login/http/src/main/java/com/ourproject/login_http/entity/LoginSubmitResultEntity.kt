package com.ourproject.login_http.entity

class LoginSubmitResultEntity(
    val loginData: LoginData = LoginData.DEFAULT,
    val meta: Meta? = Meta.DEFAULT
){
    companion object{
        val DEFAULT = LoginSubmitResultEntity(
            loginData = LoginData.DEFAULT,
            meta = Meta.DEFAULT
        )
    }
}

data class LoginData(
    val accessToken: String,
    val tokenType: String,
    val user: User
) {
    companion object {
        val DEFAULT = LoginData(
            accessToken = "defaultAccessToken",
            tokenType = "defaultTokenType",
            user = User.DEFAULT
        )
    }
}

data class Meta(
    val code: Int,
    val message: String,
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

data class User(
    val profilePhotoUrl: String,
    val address: String,
    val city: String,
    val roles: String,
    val houseNumber: String,
    val createdAt: Long,
    val emailVerifiedAt: Any? = null,
    val currentTeamId: Any? = null,
    val phoneNumber: String,
    val updatedAt: Long,
    val name: String,
    val id: Int,
    val profilePhotoPath: Any? = null,
    val email: String
) {
    companion object {
        val DEFAULT = User(
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