package com.ourproject.login_module.feed.db

import com.ourproject.register_module.datasource.db.GofoodRegisterLocalClient
import com.ourproject.register_module.datasource.db.RegisterDao
import com.ourproject.register_module.datasource.http.dto.UserLocal
import com.ourproject.register_module.domain.GofoodRegisterLocalResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LoginFeedRoomClient constructor(
    private val loginDao: RegisterDao
) : GofoodLoginLocalClient{
    override suspend fun insert(dataInsert: UserLocal) {
        loginDao.insertUserData(dataInsert)
    }

    override fun getUserData(): Flow<LoginLocalResult> {
        return flow{
            loginDao.getUserData()
                .catch {
                    emit(LoginLocalResult.Failure(it))
                }
                .collect{
                    emit(LoginLocalResult.Success(it))
                }
        }
    }

}