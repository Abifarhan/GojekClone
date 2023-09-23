package com.ourproject.register_module.composite

import android.util.Log
import com.ourproject.register_module.datasource.http.HttpClientResult
import com.ourproject.register_module.datasource.http.RegisterFeedLoader
import com.ourproject.register_module.datasource.http.HttpRegisterClientResult
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GoFoodRegisterFactory {

    companion object {
        fun createCompositeFactory(
            primary: RegisterFeedLoader,
            fallback: RegisterFeedLoader? = null
        ): RegisterFeedLoader {
            return RegisterFeedLoaderComposite(primary, fallback!!)
        }
    }
}


class RegisterFeedLoaderComposite(
    private val primary: RegisterFeedLoader,
    private val fallback: RegisterFeedLoader
) : RegisterFeedLoader {
    override fun submit(userData: RegistrationEntity): Flow<HttpClientResult> {
        return flow {
            primary.submit(userData).collect{
                try {

                    Log.d("TAG", "submit: here the result of submit $it")
                    primary.submit(userData).collect {
                        when (it) {
                            is HttpClientResult.Success -> emit(it)
                            is HttpClientResult.Failure -> emit(
                                fallback.submit(userData).first()
                            )
                        }
                    }
                } catch (e: Exception) {
                    Log.d("TAG", "submit: here the result of submit 2 $it")
                    fallback.submit(userData)
                }
            }
        }
    }

}