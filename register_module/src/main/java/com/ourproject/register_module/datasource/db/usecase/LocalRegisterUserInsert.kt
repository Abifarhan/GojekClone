package com.ourproject.register_module.datasource.db.usecase

import com.ourproject.register_module.datasource.db.GofoodRegisterCache
import com.ourproject.register_module.datasource.db.GofoodRegisterLocalClient
import com.ourproject.register_module.datasource.http.dto.User
import com.ourproject.register_module.datasource.http.dto.UserLocal

class LocalRegisterUserInsert constructor(
    private val registerLocalClient: GofoodRegisterLocalClient
) : GofoodRegisterCache {
    override suspend fun save(data: UserLocal) {
        registerLocalClient.insert(data)
    }

}

