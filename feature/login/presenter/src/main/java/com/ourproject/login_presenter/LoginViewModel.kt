package com.ourproject.login_presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.login_domain.LoginSubmitDomain
import com.ourproject.login_domain.LoginUseCase
import com.ourproject.login_domain.SubmitResult
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
    private val loginInsert: LoginUseCase
) : ViewModel() {



    private val _userDataLiveData = MutableStateFlow(UserStateLogin())
    val userDataLiveData: StateFlow<UserStateLogin> = _userDataLiveData.asStateFlow()
    fun login(inputLoginForm : UserInputDataLogin){
        viewModelScope.launch {
            _userDataLiveData.update {
                it.copy(isLoading = true)
            }
            loginInsert.login(
                LoginSubmitDomain(
                email = inputLoginForm.email,
                password = inputLoginForm.password
                )
            ).collect{ result ->

                _userDataLiveData.update {
                    when(result){

                        is SubmitResult.Success -> {
                            it.copy(
                                isLoading = false,
                                userRegistered = true
                            )
                        }
                        is SubmitResult.Failure -> {
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