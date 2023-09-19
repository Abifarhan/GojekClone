package com.ourproject.register_module.composite

import android.util.Log
import com.ourproject.register_module.datasource.db.GofoodRegisterCache
import com.ourproject.register_module.datasource.http.GoPayRegisterLoader
import com.ourproject.register_module.datasource.http.HttpRegisterClientResult
import com.ourproject.register_module.datasource.http.dto.RegistrationData
import com.ourproject.register_module.datasource.http.dto.User
import com.ourproject.register_module.datasource.http.dto.UserLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GoFoodRegisterLoaderCacheDecorator(
    private val decorate: GoPayRegisterLoader,
    private val cache: GofoodRegisterCache
) : GoPayRegisterLoader{
    override fun submit(userData: RegistrationData): Flow<HttpRegisterClientResult> {
        return flow {
            decorate.submit(userData).collect { result ->

                Log.d("TAG", "submit: the total data you get is $result")
                if (result is HttpRegisterClientResult.Success) {
                    val userLocal = result.root.data.user.toUserLocal()
                    cache.save(userLocal)
                }
                emit(result)
            }
        }
    }


    fun User.toUserLocal(): UserLocal {
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