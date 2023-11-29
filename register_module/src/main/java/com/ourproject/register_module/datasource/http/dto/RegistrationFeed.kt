package com.ourproject.register_module.datasource.http.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

data class RegistrationDto(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String,
    val address: String,
    val city: String,
    val houseNumber: String,
    val phoneNumber: String
)

data class RegistrationEntity(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String,
    val address: String,
    val city: String,
    val houseNumber: String,
    val phoneNumber: String
)



// DTO section
data class ResponseDataDto(
    val meta: MetaDataDto,
    val data: UserDataDto
) {
    companion object{
        val DEFAULT = ResponseDataDto(
            meta  = MetaDataDto.DEFAULT,
            data = UserDataDto.DEFAULT
        )
    }
}

data class MetaDataDto(
    val code: Int = 0,
    val status: String = "",
    val message: String = ""
) {
    companion object {
        val DEFAULT = MetaDataDto(
            code = 200,
            status = "OK",
            message = "Default message"
        )
    }
}

data class UserDataDto(
    val access_token: String = "",
    val token_type: String = "",
    val user: UserDto = UserDto.DEFAULT
) {
    companion object {
        val DEFAULT = UserDataDto(
            access_token = "default_token",
            token_type = "default_type",
            user = UserDto.DEFAULT
        )
    }
}

data class UserDto(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val address: String = "",
    val houseNumber: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val profile_photo_url: String? = null
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
            profile_photo_url = "default_url"
        )
    }
}

// Entity section
data class ResponseDataEntity(
    val meta: MetaDataEntity = MetaDataEntity.DEFAULT,
    val data: UserDataEntity = UserDataEntity.DEFAULT
) {
    companion object {
        val DEFAULT = ResponseDataEntity(
            MetaDataEntity.DEFAULT,
            UserDataEntity.DEFAULT
        )
    }
}

data class MetaDataEntity(
    val code: Int = 0,
    val status: String = "",
    val message: String = ""
) {
    companion object {
        val DEFAULT = MetaDataEntity(
            code = 200,
            status = "OK",
            message = "Default message"
        )
    }
}

data class UserDataEntity(
    val access_token: String = "",
    val token_type: String = "",
    val user: UserEntity = UserEntity.DEFAULT
) {
    companion object {
        val DEFAULT = UserDataEntity(
            access_token = "default_token",
            token_type = "default_type",
            user = UserEntity.DEFAULT
        )
    }
}

data class UserEntity(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val address: String = "",
    val houseNumber: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val profile_photo_url: String? = null
) {
    companion object {
        val DEFAULT = UserEntity(
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


@Entity(tableName = "register_feed")
data class UserLocal(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val address: String,
    val houseNumber: String,
    val phoneNumber: String,
    val city: String,
){
    companion object {
        val DEFAULT = UserLocal(
            id = 12344,
            name = "Default Name",
            email = "default@example.com",
            address = "Default Address",
            houseNumber = "123",
            phoneNumber = "555-1234",
            city = "Default City"
        )
    }
}
