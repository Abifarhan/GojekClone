package com.ourproject.register_module.presentation

import android.content.res.Resources
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
import com.ourproject.register_module.datasource.http.usecase.BadRequest
import com.ourproject.register_module.datasource.http.usecase.Connectivity
import com.ourproject.register_module.datasource.http.usecase.InternalServerError
import com.ourproject.register_module.datasource.http.usecase.InvalidData
import com.ourproject.register_module.datasource.http.usecase.NotFound
import com.ourproject.register_module.domain.GofoodLoader
import com.ourproject.register_module.domain.GofoodRegisterLocalResult
import com.ourproject.register_module.factory.GofoodRegisterLocalInsertFactory
import com.ourproject.register_module.factory.RemoteRegisterLoaderFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.chromium.net.NetworkException


data class UserState(
    val isLoading: Boolean = false,
    val failedMessage: String = "",
    val userRegistered: Boolean = false
)
class RegisterFeedViewModel constructor(
    private val goPayRegisterLoader: RegisterFeedLoader,
    private val gopayResultRegisterLoader: GofoodLoader
): ViewModel(){

    private val _userDataLiveData = MutableLiveData<UserLocal>()
    val userDataLiveData: LiveData<UserLocal> = _userDataLiveData


    private val _isUserRegistered = MutableStateFlow(UserState())
    val isUserRegistered: StateFlow<UserState> = _isUserRegistered.asStateFlow()



    fun submitUserRegister(registrationData: RegistrationEntity) {
        viewModelScope.launch {

            _isUserRegistered.update {
                it.copy(isLoading = true)
            }

            goPayRegisterLoader.submit(registrationData).collect{ result->
                _isUserRegistered.update {
                    when (result) {
                        is RegisterFeedResult.Success -> {
                            it.copy(
                                isLoading = false,
                                userRegistered = true
                            )
                        }

                        is RegisterFeedResult.Failure -> {
                            it.copy(
                                isLoading = false,
                                failedMessage = when (result.throwable) {
                                    is Connectivity -> "Tidak ada internet"
                                    is InvalidData -> {
                                        "Terjadi kesalahan, coba lagi"
                                    }
                                    is BadRequest -> "Permintaan gagal, coba lagi"
                                    is NotFound -> "Tidak ditemukan, coba lagi"
                                    is InternalServerError -> "Server sedang dalam perbaikan"
                                    else -> { "Terjadi kesalahan, coba lagi" }
                                }
                            )
                        }
                    }
                }
            }
//            try {
//
////                goPayRegisterLoader.submit(registrationData)
////                    .collect { result ->
////
////                    when (result) {
////                        is RegisterFeedResult.Success -> {
////                            _isUserRegistered.postValue(true)
////                        }
////
////                        is RegisterFeedResult.Failure -> {
////                            _isUserRegistered.postValue(false)
////                        }
////                    }
////                }
//
//                goPayRegisterLoader
//            } catch (e: Exception) {
//            }
        }
    }

    fun fetchUserDataLocal() {
        viewModelScope.launch {
            gopayResultRegisterLoader.loadUserData().collect { result ->
                when (result) {
                    is GofoodRegisterLocalResult.Success -> {
                        _userDataLiveData.value = result.userData
                    }

                    is GofoodRegisterLocalResult.Failure -> {
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