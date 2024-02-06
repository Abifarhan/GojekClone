package com.ourproject.login_domain


data class UserDataDomain(
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
        val DEFAULT = UserDataDomain(
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