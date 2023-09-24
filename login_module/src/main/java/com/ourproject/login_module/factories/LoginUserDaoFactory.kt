package com.ourproject.login_module.factories

import com.ourproject.login_module.feed.db.LoginFeedRoomClient
import com.ourproject.login_module.feed.db.GofoodLoginLocalClient
import com.ourproject.register_module.frameworks.LocalFactory

class LoginUserDaoFactory {

    companion object {
        fun createLoginUserDao(): GofoodLoginLocalClient {
            return LoginFeedRoomClient(LocalFactory.createDatabase().registerDao())
        }
    }
}