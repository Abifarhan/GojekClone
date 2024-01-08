package com.ourproject.register_presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.register_domain.SubmitResult
import com.ourproject.register_domain.api.RegisterUserCase
import com.ourproject.register_domain.api.RegisterSubmitEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserState(
    val isLoading: Boolean = false,
    val failedMessage: String = "",
    val userRegistered: Boolean = false
)
class RegisterViewModel constructor(
    private val registerSubmit: RegisterUserCase
): ViewModel(){
//
    private val _isUserRegistered = MutableStateFlow(UserState())
    val isUserRegistered: StateFlow<UserState> = _isUserRegistered.asStateFlow()

    private var _emailUser : MutableLiveData<String> = MutableLiveData()

    val emailUser = _emailUser

    fun submitRegister(
        registerSubmitData: RegisterSubmitEntity
    ) {
        viewModelScope.launch {

            _isUserRegistered.update {
                it.copy(isLoading = true)
            }

            registerSubmit.register(
                registerSubmitDto = registerSubmitData
            ).collect { result ->

                _isUserRegistered.update {
                    when (result) {
                        is SubmitResult.Success -> {
                            it.copy(
                                isLoading = false,
                                userRegistered = true
                            )
                        }

                        is SubmitResult.Failure -> {
                            it.copy(
                                isLoading = false,
                                failedMessage = result.errorMessage
                            )
                        }
                    }
                }
                when(result){
                    is SubmitResult.Success -> {
                        _emailUser.postValue(registerSubmitData.email)
                    }
                    is SubmitResult.Failure -> {
                        _emailUser.postValue("")
                    }
                    else -> {
                        _emailUser.postValue("")
                    }
                }

            }
        }
    }
}