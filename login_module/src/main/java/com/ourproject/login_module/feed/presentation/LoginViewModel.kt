package com.ourproject.login_module.feed.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ourproject.login_module.composite.LoginLoaderSessionDecorator
import com.ourproject.login_module.factories.LoginFeedLocalInsertFactory
import com.ourproject.login_module.factories.RemoteLoginFeedLoaderFactory
import com.ourproject.login_module.feed.domain.LoginFeedLoader
import com.ourproject.login_module.feed.domain.LoginFeedResult
import com.ourproject.login_module.feed.domain.LoginSubmitEntity
import com.ourproject.register_module.datasource.db.usecase.LocalRegisterFeedLoaderFactory
import com.ourproject.register_module.datasource.http.dto.UserLocal
import com.ourproject.register_module.datasource.http.usecase.BadRequest
import com.ourproject.register_module.datasource.http.usecase.Connectivity
import com.ourproject.register_module.datasource.http.usecase.InternalServerError
import com.ourproject.register_module.datasource.http.usecase.InvalidData
import com.ourproject.register_module.datasource.http.usecase.NotFound
import com.ourproject.register_module.domain.GofoodLoader
import com.ourproject.register_module.domain.GofoodRegisterLocalResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.chromium.net.NetworkException

data class UserState(
    val isLoading: Boolean = false,
    val failedMessage: String = "",
    val userRegistered: Boolean = false
)


class LoginViewModel constructor(
    private val loginFeedLoader: LoginFeedLoader,
    private val gopayResulRegisterLoader: GofoodLoader
) : ViewModel() {
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    private val _userDataLiveData = MutableStateFlow(UserState())
    val userDataLiveData: StateFlow<UserState> = _userDataLiveData.asStateFlow()
    fun submitDataUser(submitEntity: LoginSubmitEntity) {
        viewModelScope.launch {

            _userDataLiveData.update {
                it.copy(isLoading = true)
            }

            try {
                loginFeedLoader.submit(submitEntity).collect{result ->

                    _userDataLiveData.update {
                        when (result) {
                            is LoginFeedResult.Success -> {
                                it.copy(
                                    isLoading = false,
                                    userRegistered = true
                                )

                            }

                            is LoginFeedResult.Failure -> {
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
            } catch (e: NetworkException) {
                // Handle network-related exceptions (e.g., no connectivity)
                Log.e("TAG", "submitUserRegister: Network Exception: ${e.message}")
                // Show a message to the user indicating a network issue
            } catch (e: Exception) {
                // Handle other general exceptions
                Log.e("TAG", "submitUserRegister: Exception: ${e.message}")
                //
            }
        }
    }


    fun checkSession() {
        viewModelScope.launch {
            gopayResulRegisterLoader.loadUserData().collect { result ->
                when (result) {
                    is GofoodRegisterLocalResult.Success -> {
                        Log.d("TAG", "fetch data local status is ${result.userData}}")
//                        _userDataLiveData.value = result.userData
                    }

                    is GofoodRegisterLocalResult.Failure -> {
                        Log.e("TAG", "fetch data local status is 2 ${result.throwable.message}}")
                    }
                }
            }
        }
    }

    companion object {
        val FACTORY: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel(
                    loginFeedLoader =  LoginLoaderSessionDecorator(
                        decorate = RemoteLoginFeedLoaderFactory.createRemoteLoginFeedLoader(),
                        session = LoginFeedLocalInsertFactory.createLocalInsertUserData()
                    ),
                    gopayResulRegisterLoader = LocalRegisterFeedLoaderFactory.createLocalCryptoRegisterFeedLoader()
                )
            }
        }
    }
}