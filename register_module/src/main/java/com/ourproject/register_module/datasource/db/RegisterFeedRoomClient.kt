package com.ourproject.register_module.datasource.db

import com.ourproject.register_module.datasource.http.dto.User
import com.ourproject.register_module.datasource.http.dto.UserLocal
import com.ourproject.register_module.domain.GofoodRegisterLocalResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class RegisterFeedRoomClient constructor(
    private val registerDao: RegisterDao
) : GofoodRegisterLocalClient {
    override suspend fun insert(dataInsert: UserLocal) {
        registerDao.insertUserData(dataInsert)
    }

    override fun getUserData(): Flow<GofoodRegisterLocalResult> {
        return flow {
            registerDao.getUserData()
                .catch {
                    emit(GofoodRegisterLocalResult.Failure(it))
                }.collect {
                    emit(GofoodRegisterLocalResult.Success(it))
                }
        }
    }

}