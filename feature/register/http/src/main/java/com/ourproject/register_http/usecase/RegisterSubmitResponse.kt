package com.ourproject.register_http.usecase


data class RegisterSubmitResponse(
    val profilePhotoUrl: String? = null,

    val address: String = "",

    val city: String = "",

    val roles: String = "",

    val houseNumber: String = "",

    val createdAt: Long = 0,

    val emailVerifiedAt: Any? = null,

    val currentTeamId: Any? = null,

    val phoneNumber: String = "",

    val updatedAt: Long = 0,

    val name: String = "",

    val id: Int = 0,

    val profilePhotoPath: Any? = null,

    val email: String = ""
)
