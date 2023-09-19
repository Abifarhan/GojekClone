package com.ourproject.register_module.datasource.db

import com.ourproject.register_module.datasource.http.dto.RegistrationData
import com.ourproject.register_module.datasource.http.dto.User
import com.ourproject.register_module.datasource.http.dto.UserLocal

interface GofoodRegisterLocalClient {

    suspend fun insert(dataInsert: UserLocal)
}