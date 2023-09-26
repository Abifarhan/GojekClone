package com.ourproject.login_module.composite

import com.ourproject.login_module.feed.db.LoginSession
import com.ourproject.login_module.feed.domain.LoginFeedLoader
import com.ourproject.login_module.feed.domain.LoginFeedResult
import com.ourproject.login_module.feed.domain.LoginSubmitEntity
import com.ourproject.login_module.feed.domain.LoginMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginLoaderSessionDecorator(
    private val decorate: LoginFeedLoader,
    private val session: LoginSession
) : LoginFeedLoader{
    override fun submit(loginSubmitEntity: LoginSubmitEntity): Flow<LoginFeedResult> {
        return flow {
            decorate.submit(loginSubmitEntity).collect { result ->
                if (result is LoginFeedResult.Success) {
                    val userLocal = result.loginResultEntity.data.user
                    session.save(LoginMapper.mapUserEntityToLocal(userLocal))
                }
                emit(result)
            }
        }
    }


}