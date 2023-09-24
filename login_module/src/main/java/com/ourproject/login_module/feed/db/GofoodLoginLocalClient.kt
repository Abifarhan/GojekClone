package com.ourproject.login_module.feed.db

import kotlinx.coroutines.flow.Flow
import com.ourproject.register_module.datasource.http.dto.UserLocal
interface GofoodLoginLocalClient {
    suspend fun insert(dataInsert: UserLocal)

    fun getUserData(): Flow<LoginLocalResult>
}


sealed class LoginLocalResult {
    data class Success(val userData: UserLocal): LoginLocalResult()

    data class Failure(val throwable: Throwable): LoginLocalResult()
}

