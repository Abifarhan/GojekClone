package com.ourproject.register_module.composite

import android.util.Log
import com.ourproject.register_module.datasource.db.GofoodRegisterCache
import com.ourproject.register_module.datasource.http.HttpClientResult
import com.ourproject.register_module.datasource.http.RegisterFeedLoader
import com.ourproject.register_module.datasource.http.HttpRegisterClientResult
import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.datasource.http.dto.UserDto
import com.ourproject.register_module.datasource.http.dto.UserLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


// todo : insert Session here
class GoFoodRegisterLoaderCacheDecorator(
    private val decorate: RegisterFeedLoader,
    private val cache: GofoodRegisterCache
) : RegisterFeedLoader{
    override fun submit(userData: RegistrationEntity): Flow<HttpClientResult> {
        return flow {
            decorate.submit(userData).collect { result ->

                Log.d("TAG", "submit: the total data you get is $result")
                if (result is HttpClientResult.Success) {
                    val userLocal = result.root.data.user.toUserLocal()
                    cache.save(userLocal)
                }
                emit(result)
            }
        }
    }


    fun UserDto.toUserLocal(): UserLocal {
        return UserLocal(
            id = this.id,
            name = this.name,
            email = this.email,
            address = this.address,
            houseNumber = this.houseNumber,
            phoneNumber = this.phoneNumber,
            city = this.city,
            profile_photo_url = this.profile_photo_url
        )
    }
}