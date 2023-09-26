package com.ourproject.register_module.datasource.db.usecase

import com.ourproject.register_module.datasource.db.GofoodRegisterLocalClient
import com.ourproject.register_module.domain.GofoodLoader
import com.ourproject.register_module.domain.GofoodRegisterLocalResult
import com.ourproject.register_module.factory.RegisterUserDaoFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LocalRegisterFeedLoaderFactory {

    companion object{
        fun createLocalCryptoRegisterFeedLoader(): GofoodLoader {
            return GofoodRegisterLocalLoader(
                RegisterUserDaoFactory.createRegisterUserDao()
            )
        }
    }
}


class GofoodRegisterLocalLoader constructor(
    private val registerLocalClient: GofoodRegisterLocalClient
) : GofoodLoader {
    override fun loadUserData(): Flow<GofoodRegisterLocalResult> {
        return flow {
            registerLocalClient.getUserData()
                .catch {
                    emit(GofoodRegisterLocalResult.Failure(it))
                }
                .collect{ result ->
                    when (result) {
                        is GofoodRegisterLocalResult.Success -> {
                            emit(GofoodRegisterLocalResult.Success(result.userData))
                        }
                        is GofoodRegisterLocalResult.Failure -> {
                            emit(GofoodRegisterLocalResult.Failure(result.throwable))
                        }
                    }
                }
        }
    }


}