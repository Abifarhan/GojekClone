package com.ourproject.login_module.composite

import com.ourproject.login_module.feed.domain.LoginFeedLoader
import com.ourproject.login_module.feed.domain.LoginFeedResult
import com.ourproject.login_module.feed.domain.LoginSubmitEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow


class LoginFeedLoaderFactory {


    companion object {

        fun createCompositeFactory(
            primary: LoginFeedLoader,
            fallback: LoginFeedLoader
        ): LoginFeedLoader {
            return LoginFeedLoaderComposite(primary, fallback)
        }
    }
}


class LoginFeedLoaderComposite(
    private val primary: LoginFeedLoader,
    private val fallback: LoginFeedLoader
) : LoginFeedLoader {
    override fun submit(loginSubmitEntity: LoginSubmitEntity): Flow<LoginFeedResult> {
        return flow {
            primary.submit(loginSubmitEntity).collect{
                try {
                    primary.submit(loginSubmitEntity).collect {
                        when (it) {
                            is LoginFeedResult.Success -> emit(it)
                            is LoginFeedResult.Failure -> emit(
                                fallback.submit(loginSubmitEntity).first()
                            )
                        }
                    }
                } catch (e: Exception) {
                    fallback.submit(loginSubmitEntity)
                }
            }
        }
    }

}