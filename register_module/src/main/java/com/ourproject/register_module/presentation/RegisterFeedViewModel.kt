package com.ourproject.register_module.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ourproject.register_module.composite.GoFoodRegisterLoaderSessionDecorator
import com.ourproject.register_module.datasource.db.usecase.LocalRegisterFeedLoaderFactory
import com.ourproject.register_module.datasource.http.RegisterFeedLoader
import com.ourproject.register_module.datasource.http.RegisterFeedResult
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.datasource.http.dto.UserLocal
import com.ourproject.register_module.domain.GofoodLoader
import com.ourproject.register_module.domain.GofoodRegisterLocalResult
import com.ourproject.register_module.factory.GofoodRegisterLocalInsertFactory
import com.ourproject.register_module.factory.RemoteRegisterLoaderFactory
import kotlinx.coroutines.launch
import org.chromium.net.NetworkException

class RegisterFeedViewModel constructor(
    private val goPayRegisterLoader: RegisterFeedLoader,
    private val gopayResultRegisterLoader: GofoodLoader
): ViewModel(){

    private val _userDataLiveData = MutableLiveData<UserLocal>()
    val userDataLiveData: LiveData<UserLocal> = _userDataLiveData


    private val _isUserRegistered = MutableLiveData<Boolean>()
    val isUserRegistered: LiveData<Boolean> = _isUserRegistered


    fun submitUserRegister(registrationData: RegistrationEntity) {
        viewModelScope.launch {
            try {
                Log.d("TAG", "submitUserRegister: this operation executed")

                goPayRegisterLoader.submit(registrationData)
                    .collect { result ->

                    when (result) {
                        is RegisterFeedResult.Success -> {
                            _isUserRegistered.postValue(true)
                            Log.d("TAG", "submitUserRegister: Exception within Flow: 1 ${result.root}")
                        }

                        is RegisterFeedResult.Failure -> {
                            _isUserRegistered.postValue(false)
                            Log.e("TAG", "submitUserRegister: Exception within Flow: 2 ${result.throwable.message}")
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

    fun fetchUserDataLocal() {
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

    companion object{
        val FACTORY : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RegisterFeedViewModel(
                    goPayRegisterLoader =  GoFoodRegisterLoaderSessionDecorator(
                            decorate = RemoteRegisterLoaderFactory.createRemoteRegisterUserLoader(),
                            session = GofoodRegisterLocalInsertFactory.createLocalInsertUserdata()
                        ),
                    gopayResultRegisterLoader = LocalRegisterFeedLoaderFactory.createLocalCryptoRegisterFeedLoader()
                )
            }
        }
    }
}