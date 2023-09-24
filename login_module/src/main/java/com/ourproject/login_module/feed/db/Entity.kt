package com.ourproject.login_module.feed.db

import androidx.room.Entity
import androidx.room.PrimaryKey

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