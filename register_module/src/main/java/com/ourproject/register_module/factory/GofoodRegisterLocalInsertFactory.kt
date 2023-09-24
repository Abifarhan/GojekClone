package com.ourproject.register_module.factory

import com.ourproject.register_module.datasource.db.RegisterSession
import com.ourproject.register_module.datasource.db.usecase.LocalRegisterUserInsert

class GofoodRegisterLocalInsertFactory {

    companion object {
        fun createLocalInsertUserdata(): RegisterSession {
            return LocalRegisterUserInsert(RegisterUserDaoFactory.createRegisterUserDao())
        }
    }
}