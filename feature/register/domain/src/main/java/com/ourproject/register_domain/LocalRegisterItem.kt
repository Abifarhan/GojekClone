package com.ourproject.register_domain


data class UserDataDomain(
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
) {
    companion object {
        val DEFAULT = UserDataDomain(
            profilePhotoUrl = "default_url",
            address = "Default Address",
            city = "Default City",
            roles = "",
            houseNumber = "123",
            createdAt = 0,
            emailVerifiedAt = null,
            currentTeamId = null,
            phoneNumber = "555-1234",
            updatedAt = 0,
            name = "Default Name",
            id = 12344,
            profilePhotoPath = null,
            email = "default@example.com"
        )
    }
}
