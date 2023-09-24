package com.ourproject.register_module.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.register_module.datasource.http.HttpClientResult
import com.ourproject.register_module.datasource.http.RegisterFeedLoader
import com.ourproject.register_module.datasource.http.RegisterFeedResult
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.datasource.http.dto.UserLocal
import com.ourproject.register_module.domain.GofoodRegisterLoader
import com.ourproject.register_module.domain.GofoodRegisterLocalResult
import com.ourproject.session_module.SessionManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.chromium.net.NetworkException

class RegisterFeedViewModel constructor(
    private val goPayRegisterLoader: RegisterFeedLoader,
    private val gopayResultRegisterLoader: GofoodRegisterLoader
): ViewModel(){

    private val _userDataLiveData = MutableLiveData<UserLocal>()
    val userDataLiveData: LiveData<UserLocal> = _userDataLiveData
    fun submitUserRegister(registrationData: RegistrationEntity) {
        viewModelScope.launch {
            try {
                Log.d("TAG", "submitUserRegister: this operation executed")

                goPayRegisterLoader.submit(registrationData)
                    .catch { e ->
                        Log.e("TAG", "submitUserRegister: Exception within Flow: ${e.message}")
                    }
                    .collect { result ->
                        when (result) {
                            is RegisterFeedResult.Success -> {
                                Log.e("TAG", "submitUserRegister: Exception within Flow: 1 ${result.root}")
                            }

                            is RegisterFeedResult.Failure -> {
                                Log.e("TAG", "submitUserRegister: Exception within Flow: 1 ${result.throwable.message}")
                            }
                        }
                    }
            } catch (e: NetworkException) {
                Log.e("TAG", "submitUserRegister: Network Exception: ${e.message}")
            } catch (e: Exception) {
                Log.e("TAG", "submitUserRegister: Exception: ${e.message}")
            }
        }



    }

    fun fetchUserDataLocal(context: Context) {
        viewModelScope.launch {
            gopayResultRegisterLoader.loadUserData().collect { result ->
                when (result) {
                    is GofoodRegisterLocalResult.Success -> {
                        Log.d("TAG", "fetch data local status is ${result.userData}}")
                        _userDataLiveData.value = result.userData
                    }

                    is GofoodRegisterLocalResult.Failure -> {
                        Log.e("TAG", "fetch data local status is 2 ${result.throwable.message}}")
                    }
                }
            }
        }
    }
}