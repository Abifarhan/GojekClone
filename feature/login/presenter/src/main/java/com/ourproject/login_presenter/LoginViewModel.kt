package com.ourproject.login_presenter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.login_domain.LoginSubmit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserStateLogin(
    val isLoading: Boolean = false,
    val failedMessage: String = "",
    val userRegistered: Boolean = false
)
class LoginViewModel constructor(
    private val loginInsert: LoginSubmit
) : ViewModel() {


    private var _emailUser : MutableLiveData<String> = MutableLiveData()

    val emailUser = _emailUser

    private val _userDataLiveData = MutableStateFlow(UserStateLogin())
        val userDataLiveData: StateFlow<UserStateLogin> = _userDataLiveData.asStateFlow()
    fun login(email: String, password: String){
        viewModelScope.launch {
            _userDataLiveData.update {
                it.copy(isLoading = true)
            }
            loginInsert.login(com.ourproject.login_domain.LoginSubmitEntity(
                email = email,
                password = password
            )).collect{ result ->
                Log.d("TAG", "login: login operation result is $result")
//                _login.value = result.

                _userDataLiveData.update {
                    when(result){

                        is SubmitResult.Success -> {
                            _emailUser.postValue(email)
                            it.copy(
                                isLoading = false,
                                userRegistered = true
                            )
                        }
                        is SubmitResult.Failure -> {
                            _emailUser.postValue("")
                            it.copy(
                                isLoading = false,
                                userRegistered = false,
                                failedMessage = result.errorMessage
                            )
                        }

                        else -> {
                            it.copy(
                                isLoading = false,
                                userRegistered = true
                            )
                        }
                    }
                }


            }
        }
    }
}