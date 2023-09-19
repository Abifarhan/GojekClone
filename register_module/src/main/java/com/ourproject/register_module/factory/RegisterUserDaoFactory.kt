package com.ourproject.register_module.factory

import com.ourproject.register_module.datasource.db.GofoodRegisterLocalClient
import com.ourproject.register_module.datasource.db.RegisterFeedRoomClient
import com.ourproject.register_module.frameworks.LocalFactory

class RegisterUserDaoFactory {

    companion object{
        fun createRegisterUserDao(): GofoodRegisterLocalClient {
            return RegisterFeedRoomClient(LocalFactory.createDatabase().registerDao())
        }
    }
}