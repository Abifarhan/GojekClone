package com.ourproject.register_module.datasource.http

data class User(
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

data class ResponseData(
    val meta: MetaData,
    val data: UserData
)

data class MetaData(
    val code: Int,
    val status: String,
    val message: String
)

data class UserData(
    val access_token: String,
    val token_type: String,
    val user: User
)

