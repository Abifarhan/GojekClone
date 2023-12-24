package com.ourproject.register_module.presentation

import androidx.lifecycle.ViewModel
import com.ourproject.register_module.datasource.http.RegisterFeedLoader

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class UserState(
    val isLoading: Boolean = false,
    val failedMessage: String = "",
    val userRegistered: Boolean = false
)
class RegisterViewModel constructor(
    private val goPayRegisterLoader: RegisterFeedLoader,
//    private val gopayResultRegisterLoader: GofoodLoader
): ViewModel(){

//    private val _userDataLiveData = MutableLiveData<UserLocal>()
//    val userDataLiveData: LiveData<UserLocal> = _userDataLiveData


    private val _isUserRegistered = MutableStateFlow(UserState())
    val isUserRegistered: StateFlow<UserState> = _isUserRegistered.asStateFlow()



//    fun submitUserRegister(registrationData: gt.RegistrationEntity) {
//        viewModelScope.launch {
//
//            _isUserRegistered.update {
//                it.copy(isLoading = true)
//            }
//
//            goPayRegisterLoader.submit(registrationData).collect{ result->
//                _isUserRegistered.update {
//                    when (result) {
//                        is RegisterFeedResult.Success -> {
//                            it.copy(
//                                isLoading = false,
//                                userRegistered = true
//                            )
//                        }
//
//                        is RegisterFeedResult.Failure -> {
//                            it.copy(
//                                isLoading = false,
//                                failedMessage = when (result.throwable) {
//                                    is Connectivity -> "Tidak ada internet"
//                                    is InvalidData -> {
//                                        "Terjadi kesalahan, coba lagi"
//                                    }
//                                    is BadRequest -> "Permintaan gagal, coba lagi"
//                                    is NotFound -> "Tidak ditemukan, coba lagi"
//                                    is InternalServerError -> "Server sedang dalam perbaikan"
//                                    else -> { "Terjadi kesalahan, coba lagi" }
//                                }
//                            )
//                        }
//                    }
//                }
//            }
////            try {
////
//////                goPayRegisterLoader.submit(registrationData)
//////                    .collect { result ->
//////
//////                    when (result) {
//////                        is RegisterFeedResult.Success -> {
//////                            _isUserRegistered.postValue(true)
//////                        }
//////
//////                        is RegisterFeedResult.Failure -> {
//////                            _isUserRegistered.postValue(false)
//////                        }
//////                    }
//////                }
////
////                goPayRegisterLoader
////            } catch (e: Exception) {
////            }
//        }
//    }

//    fun fetchUserDataLocal() {
//        viewModelScope.launch {
//            gopayResultRegisterLoader.loadUserData().collect { result ->
//                when (result) {
//                    is GofoodRegisterLocalResult.Success -> {
//                        _userDataLiveData.value = result.userData
//                    }
//
//                    is GofoodRegisterLocalResult.Failure -> {
//                    }
//                }
//            }
//        }
//    }

//    companion object{
//        val FACTORY : ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                RegisterFeedViewModel(
//                    goPayRegisterLoader =  GoFoodRegisterLoaderSessionDecorator(
//                            decorate = RemoteRegisterLoaderFactory.createRemoteRegisterUserLoader(),
//                            session = GofoodRegisterLocalInsertFactory.createLocalInsertUserdata()
//                        ),
//                    gopayResultRegisterLoader = LocalRegisterFeedLoaderFactory.createLocalCryptoRegisterFeedLoader()
//                )
//            }
//        }
//    }
}