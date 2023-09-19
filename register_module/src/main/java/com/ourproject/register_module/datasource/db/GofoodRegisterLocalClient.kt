package com.ourproject.register_module.datasource.db

import com.ourproject.register_module.datasource.http.dto.UserLocal
import com.ourproject.register_module.domain.GofoodRegisterLocalResult
import kotlinx.coroutines.flow.Flow

interface GofoodRegisterLocalClient {

    suspend fun insert(dataInsert: UserLocal)

    fun getUserData(): Flow<GofoodRegisterLocalResult>
}