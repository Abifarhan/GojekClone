package com.ourproject.login_module.feed.domain


data class LoginSubmitEntity(
    val email: String = "",
    val password: String = ""
)

data class LoginResultEntity(
    val meta: MetaEntity = MetaEntity.DEFAULT,
    val data: DataEntity = DataEntity.DEFAULT
) {
    companion object{
        val DEFAULT = LoginResultEntity(
            meta = MetaEntity.DEFAULT,
            data = DataEntity.DEFAULT
        )
    }
}

data class MetaEntity(
    val code: Int = 0,
    val status: String = "",
    val message: String = ""
) {
    companion object {
        val DEFAULT = MetaEntity(
            code = 0,
            status = "Default Status",
            message = "Default Message"
        )
    }
}

data class DataEntity(
    val accessToken: String = "",
    val tokenType: String = "",
    val user: UserEntity = UserEntity.DEFAULT
) {
    companion object {
        val DEFAULT = DataEntity(
            accessToken = "Default Token",
            tokenType = "Default Type",
            user = UserEntity.DEFAULT
        )
    }
}

data class UserEntity(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val emailVerifiedAt: String? = null,
    val roles: String = "",
    val currentTeamId: String? = null,
    val houseNumber: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val createdAt: Int = 0,
    val updatedAt: Int = 0,
    val profilePhotoPath: String? = null,
    val profilePhotoUrl: String = ""
) {
    companion object {
        val DEFAULT = UserEntity(
            id = 12344,
            name = "Default Name",
            email = "default@example.com",
            houseNumber = "123",
            phoneNumber = "555-1234",
            city = "Default City",
            profilePhotoUrl = "default_url"
        )
    }
}

