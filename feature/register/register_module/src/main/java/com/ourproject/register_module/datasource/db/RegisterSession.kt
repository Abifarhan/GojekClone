package com.ourproject.register_module.datasource.db

import com.ourproject.register_module.datasource.http.dto.UserLocal


interface RegisterSession {

    suspend fun save(data: UserLocal)
}