package com.ourproject.register_domain

data class RegisterResultDto (
    val accessToken: String,
    val tokenType: String,
    val registerUser: RegisterUser)


data class RegisterUser(
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
)

