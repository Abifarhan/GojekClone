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

            Log.d("TAG", "submit: did this operation executed")
            val mapper = LoginMapper.mapLoginSubmitEntityToDto(loginSubmitEntity)
            loginFeedHttpClient.submitLogin(mapper).collect{ result ->
                Log.d("TAG", "submit: did this operation executed 2")
                when (result) {
                    is HttpClientResult.Success -> {
                        Log.d("loadCryptoFeed", "InvalidData success")
                        val loginFeed = result.root
                        emit(LoginFeedResult.Success(LoginMapper.mapLoginResultDtoToEntity(loginFeed)))
                    }

                    is HttpClientResult.Failure -> {
                        Log.d("loadCryptoFeed", "InvalidData Failure")
                        when (result.throwable) {
                            is ConnectivityException -> {
                                emit(LoginFeedResult.Failure(Connectivity()))
                            }

                            is InvalidDataException -> {
                                Log.d("loadCryptoFeed", "InvalidData")
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