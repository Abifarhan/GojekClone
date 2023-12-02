package com.ourproject.login_module.feed.http

import android.util.Log
import com.ourproject.login_module.feed.domain.LoginFeedLoader
import com.ourproject.login_module.feed.domain.LoginFeedResult
import com.ourproject.login_module.feed.domain.LoginSubmitEntity
import com.ourproject.login_module.feed.domain.LoginMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteLoginFeedLoader constructor(
    private val loginFeedHttpClient: LoginFeedHttpClient
) : LoginFeedLoader {
    override fun submit(loginSubmitEntity: LoginSubmitEntity): Flow<LoginFeedResult> {
        return flow {

            val mapper = LoginMapper.mapLoginSubmitEntityToDto(loginSubmitEntity)
            loginFeedHttpClient.submitLogin(mapper).collect{ result ->
                when (result) {
                    is HttpClientResult.Success -> {
                        val loginFeed = result.root
                        emit(LoginFeedResult.Success(LoginMapper.mapLoginResultDtoToEntity(loginFeed)))
                    }

                    is HttpClientResult.Failure -> {
                        when (result.throwable) {
                            is ConnectivityException -> {
                                emit(LoginFeedResult.Failure(Connectivity()))
                            }

                            is InvalidDataException -> {
                                emit(LoginFeedResult.Failure(InvalidData()))
                            }
                        }
                    }
                }
            }
        }
    }

}

class InvalidData : Throwable()
class Connectivity : Throwable()