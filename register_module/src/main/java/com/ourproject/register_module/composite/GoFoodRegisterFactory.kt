package com.ourproject.register_module.composite

import android.util.Log
import com.ourproject.register_module.datasource.http.GoPayRegisterLoader
import com.ourproject.register_module.datasource.http.HttpRegisterClientResult
import com.ourproject.register_module.datasource.http.dto.RegistrationData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GoFoodRegisterFactory {

    companion object {
        fun createCompositeFactory(
            primary: GoPayRegisterLoader,
            fallback: GoPayRegisterLoader? = null
        ): GoPayRegisterLoader {
            return RegisterFeedLoaderComposite(primary, fallback!!)
        }
    }
}


class RegisterFeedLoaderComposite(
    private val primary: GoPayRegisterLoader,
    private val fallback: GoPayRegisterLoader
) : GoPayRegisterLoader {
    override fun submit(userData: RegistrationData): Flow<HttpRegisterClientResult> {
        return flow {
            primary.submit(userData).collect{
                try {

                    Log.d("TAG", "submit: here the result of submit $it")
                    primary.submit(userData).collect {
                        when (it) {
                            is HttpRegisterClientResult.Success -> emit(it)
                            is HttpRegisterClientResult.Failure -> emit(
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