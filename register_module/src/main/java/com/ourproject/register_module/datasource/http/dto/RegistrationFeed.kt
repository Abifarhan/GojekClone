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

data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val address: String,
    val houseNumber: String,
    val phoneNumber: String,
    val city: String,
    val profile_photo_url: String?
    // Add other user properties as needed
)

data class ResponseDataDto(
    val meta: MetaDataDto,
    val data: UserDataDto
)

data class MetaDataDto(
    val code: Int,
    val status: String,
    val message: String
)

data class UserDataDto(
    val access_token: String,
    val token_type: String,
    val user: UserDto
)

// entity section
data class ResponseDataEntity(
    val meta: MetaDataEntity,
    val data: UserDataEntity
)

data class MetaDataEntity(
    val code: Int,
    val status: String,
    val message: String
)

data class UserDataEntity(
    val access_token: String,
    val token_type: String,
    val user: UserEntity
)

data class UserEntity(
    val id: Int,
    val name: String,
    val email: String,
    val address: String,
    val houseNumber: String,
    val phoneNumber: String,
    val city: String,
    val profile_photo_url: String?
    // Add other user properties as needed
)

@Entity(tableName = "register_feed")
data class UserLocal(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val address: String,
    val houseNumber: String,
    val phoneNumber: String,
    val city: String,
)
