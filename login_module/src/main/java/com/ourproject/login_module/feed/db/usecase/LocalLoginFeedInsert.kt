package com.ourproject.login_module.feed.db.usecase

import com.ourproject.login_module.feed.db.GofoodLoginLocalClient
import com.ourproject.login_module.feed.db.LoginSession
import com.ourproject.login_module.feed.db.LoginFeedLocalClient
import com.ourproject.register_module.datasource.http.dto.UserLocal


class LocalLoginFeedInsert constructor(
    private val loginFeedLocalClient: GofoodLoginLocalClient
) : LoginSession{
    override suspend fun save(email: UserLocal) {
        loginFeedLocalClient.insert(email)
    }
}