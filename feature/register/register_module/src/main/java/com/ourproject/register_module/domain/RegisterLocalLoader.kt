package com.ourproject.register_module.domain

import com.ourproject.register_module.datasource.http.dto.UserLocal
import kotlinx.coroutines.flow.Flow


sealed class GofoodRegisterLocalResult {
    data class Success(val userData : UserLocal): GofoodRegisterLocalResult()

    data class Failure(val throwable: Throwable) : GofoodRegisterLocalResult()
}
interface GofoodLoader {

    fun loadUserData(): Flow<GofoodRegisterLocalResult>
}

class InvalidDataException : Throwable()
class ConnectivityException : Throwable()