package com.ourproject.register_module.factory

import com.ourproject.register_module.datasource.db.GofoodRegisterCache
import com.ourproject.register_module.datasource.db.usecase.LocalRegisterUserInsert

class GofoodRegisterLocalInsertFactory {

    companion object {
        fun createLocalInsertUserdata(): GofoodRegisterCache {
            return LocalRegisterUserInsert(RegisterUserDaoFactory.createRegisterUserDao())
        }
    }
}