package com.ourproject.register_module.factory

import com.ourproject.register_module.datasource.db.GofoodRegisterCache
import com.ourproject.register_module.datasource.db.usecase.LocalRegisterUserInsert
import com.ourproject.register_module.datasource.http.dto.RegistrationData

class GofoodRegisterLocalInsertFactory {

    companion object {
        fun createLocalInsertUserdata(registrationData: RegistrationData): GofoodRegisterCache {
            return LocalRegisterUserInsert(RegisterUserDaoFactory.createRegisterUserDao())
        }
    }
}