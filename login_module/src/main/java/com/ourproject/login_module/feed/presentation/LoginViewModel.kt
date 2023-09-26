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
import com.ourproject.register_module.domain.GofoodLoader
import com.ourproject.register_module.domain.GofoodRegisterLocalResult
import kotlinx.coroutines.launch
import org.chromium.net.NetworkException

class LoginViewModel constructor(
    private val loginFeedLoader: LoginFeedLoader,
    private val gopayResulRegisterLoader: GofoodLoader
) : ViewModel() {
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    private val _userDataLiveData = MutableLiveData<UserLocal>()
    val userDataLiveData: LiveData<UserLocal> = _userDataLiveData
    fun submitDataUser(submitEntity: LoginSubmitEntity) {
        viewModelScope.launch {
            try {
                loginFeedLoader.submit(submitEntity).collect{result ->
                    when (result) {
                        is LoginFeedResult.Success -> {
                            _loginStatus.postValue(true)
                            Log.d("TAG", "submitDataUser: operation resul is 1 $result")
                        }

                        is LoginFeedResult.Failure -> {
                            _loginStatus.postValue(false)
                            Log.d("TAG", "submitDataUser: operation resul is 1 $result")
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
                        _userDataLiveData.value = result.userData
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