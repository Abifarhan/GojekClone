package com.ourproject.register_module.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.register_module.datasource.http.GoPayRegisterLoader
import com.ourproject.register_module.datasource.http.HttpRegisterClientResult
import com.ourproject.register_module.datasource.http.dto.RegistrationData
import com.ourproject.register_module.datasource.http.dto.UserData
import com.ourproject.register_module.datasource.http.dto.UserLocal
import com.ourproject.register_module.domain.GofoodRegisterLoader
import com.ourproject.register_module.domain.GofoodRegisterLocalResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.chromium.net.NetworkException

class RegisterFeedViewModel constructor(
    private val goPayRegisterLoader: GoPayRegisterLoader,
    private val gopayResultRegisterLoader: GofoodRegisterLoader
): ViewModel(){

    private val _userDataLiveData = MutableLiveData<UserLocal>()
    val userDataLiveData: LiveData<UserLocal> = _userDataLiveData
    fun submitUserRegister(registrationData: RegistrationData) {
        viewModelScope.launch {
            try {
                Log.d("TAG", "submitUserRegister: this operation executed")

                // Launch the Flow and handle exceptions within the Flow
                goPayRegisterLoader.submit(registrationData)
                    .catch { e ->
                        // Handle exceptions within the Flow
                        Log.e("TAG", "submitUserRegister: Exception within Flow: ${e.message}")
                    }
                    .collect { result ->
                        when (result) {
                            is HttpRegisterClientResult.Success -> {
                                Log.d("loadCryptoFeed", "success get data ahhaha $result")
                                // Handle success, e.g., update UI with result.data
                            }

                            is HttpRegisterClientResult.Failure -> {
                                Log.d("loadCryptoFeed", "failure: ${result.throwable.message}")
                                // Handle failure, e.g., show an error message
                            }
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

    fun fetchUserDataLocal() {
        viewModelScope.launch {
            gopayResultRegisterLoader.loadUserData().collect { result ->
                when (result) {
                    is GofoodRegisterLocalResult.Success -> {
                        Log.d("TAG", "fetch data local status is ${result.userData}}")
                        _userDataLiveData.value = result.userData
                    }

                    is GofoodRegisterLocalResult.Failure -> {
                        Log.e("TAG", "fetch data local status is 2 ${result.throwable}}")
                    }
                }
            }
        }
    }
}