package com.ourproject.register_module.datasource.http

//import com.ourproject.register_domain.local.ResponseDataEntity
import kotlinx.coroutines.flow.Flow


sealed class RegisterFeedResult {
//    data class Success(val root : ResponseDataEntity): RegisterFeedResult()

    data class Failure(val throwable: Throwable): RegisterFeedResult()
}

class InvalidDataException : Throwable()
class ConnectivityException : Throwable()
interface RegisterFeedLoader {

//    fun submit(userData : RegistrationEntity): Flow<RegisterFeedResult>
}