package com.ourproject.register_module.datasource.db

import com.ourproject.register_module.datasource.http.dto.User
import com.ourproject.register_module.datasource.http.dto.UserLocal

class RegisterFeedRoomClient constructor(
    private val registerDao: RegisterDao
) : GofoodRegisterLocalClient {
    override suspend fun insert(dataInsert: UserLocal) {
        registerDao.insertUserData(dataInsert)
    }

}