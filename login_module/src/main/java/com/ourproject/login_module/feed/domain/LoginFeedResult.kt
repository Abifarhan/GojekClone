package com.ourproject.login_module.feed.domain

import kotlinx.coroutines.flow.Flow

sealed class LoginFeedResult{
    data class Success(val loginResultEntity: LoginResultEntity) : LoginFeedResult()

    data class Failure(val throwable: Throwable): LoginFeedResult()
}

interface LoginFeedLoader{
    fun submit(loginSubmitEntity: LoginSubmitEntity) : Flow<LoginFeedResult>
}
