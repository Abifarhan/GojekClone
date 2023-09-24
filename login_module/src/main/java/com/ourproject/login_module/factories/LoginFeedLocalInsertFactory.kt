package com.ourproject.login_module.factories

import com.ourproject.login_module.feed.db.LoginSession
import com.ourproject.login_module.feed.db.usecase.LocalLoginFeedInsert
import com.ourproject.register_module.factory.RegisterUserDaoFactory

class LoginFeedLocalInsertFactory {

    companion object {
        fun createLocalInsertUserData(): LoginSession {
            return LocalLoginFeedInsert(LoginUserDaoFactory.createLoginUserDao())
        }
    }
}