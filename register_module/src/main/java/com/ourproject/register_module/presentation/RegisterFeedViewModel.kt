package com.ourproject.register_module.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.register_module.datasource.http.GoPayRegisterLoader
import com.ourproject.register_module.datasource.http.HttpRegisterClientResult
import com.ourproject.register_module.datasource.http.dto.RegistrationData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.chromium.net.NetworkException

class RegisterFeedViewModel constructor(
    private val goPayRegisterLoader: GoPayRegisterLoader
): ViewModel(){

    fun submitUserRegister(registrationData: RegistrationData){
        viewModelScope.launch {
            try {
                Log.d("TAG", "submitUserRegister: this operation executed")
                val result =
                    goPayRegisterLoader.submit(registrationData).first()// Collect a single result

                when (result) {
                    is HttpRegisterClientResult.Success -> {
                        Log.d("loadCryptoFeed", "success get data $result")
                        // Handle success, e.g., update UI with result.data
                    }

                    is HttpRegisterClientResult.Failure -> {
                        Log.d("loadCryptoFeed", "failure: ${result.throwable.message}")
                        // Handle failure, e.g., show an error message
                    }
                }
            } catch (e: NetworkException) {
                // Handle network-related exceptions (e.g., no connectivity)
                Log.e("TAG", "submitUserRegister: Network Exception: ${e.message}")
                // Show a message to the user indicating a network issue
            } catch (e: Exception) {
                // Handle other general exceptions
                Log.e("TAG", "submitUserRegister: Exception: ${e.message}")
                // Show a generic error message to the user
            }
        }
    }
}