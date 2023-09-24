package com.ourproject.register_module.composite

import android.util.Log
import com.ourproject.register_module.datasource.db.RegisterSession
import com.ourproject.register_module.datasource.http.HttpClientResult
import com.ourproject.register_module.datasource.http.RegisterFeedLoader
import com.ourproject.register_module.datasource.http.RegisterFeedResult
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.datasource.http.dto.UserDto
import com.ourproject.register_module.datasource.http.dto.UserLocal
import com.ourproject.register_module.domain.RegisterMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


// todo : insert Session here
class GoFoodRegisterLoaderSessionDecorator(
    private val decorate: RegisterFeedLoader,
    private val session: RegisterSession
) : RegisterFeedLoader{
    override fun submit(userData: RegistrationEntity): Flow<RegisterFeedResult> {
        return flow {
            decorate.submit(userData).collect { result ->

                Log.d("TAG", "submit: the total data you get is $result")
                if (result is RegisterFeedResult.Success) {
                    val userLocal = result.root.data.user
                    val mapper = RegisterMapper.mapUserEntityToUserLocal(userLocal)
                    session.save(mapper)
                }
                emit(result)
            }
        }
    }
}