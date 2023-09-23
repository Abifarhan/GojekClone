package com.ourproject.login_module.feed.domain

data class LoginSubmitEntity(
    val email: String,
    val password: String
)

data class LoginResultEntity(
    val meta: MetaEntity,
    val data: DataEntity
)

data class MetaEntity(
    val code: Int,
    val status: String,
    val message: String
)

data class DataEntity(
    val accessToken: String,
    val tokenType: String,
    val user: UserEntity
)

data class UserEntity(
    val id: Int,
    val name: String,
    val email: String,
    val emailVerifiedAt: String?,
    val roles: String,
    val currentTeamId: String?,
    val houseNumber: String,
    val phoneNumber: String,
    val city: String,
    val createdAt: Int,
    val updatedAt: Int,
    val profilePhotoPath: String?,
    val profilePhotoUrl: String
)
