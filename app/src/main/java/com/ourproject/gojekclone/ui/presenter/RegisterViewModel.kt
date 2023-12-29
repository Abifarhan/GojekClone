package com.ourproject.gojekclone.ui.presenter

import SubmitResult
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.login_module.feed.http.Connectivity
import com.ourproject.login_module.feed.http.InvalidData
import com.ourproject.register_domain.api.RegisterSubmit
import com.ourproject.register_domain.api.RegisterSubmitDto
import com.ourproject.session_user.PreferenceInsert
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
    private val registerSubmit: RegisterSubmit
): ViewModel(){
//
    private val _isUserRegistered = MutableStateFlow(UserState())
    val isUserRegistered: StateFlow<UserState> = _isUserRegistered.asStateFlow()

    private var _emailUser : MutableLiveData<String> = MutableLiveData()

    val emailUser = _emailUser

    fun submitRegister(
        registerSubmitDto: RegisterSubmitDto
    ) {
        viewModelScope.launch {

            _isUserRegistered.update {
                it.copy(isLoading = true)
            }

            registerSubmit.register(
                registerSubmitDto = registerSubmitDto
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
                        _emailUser.postValue(registerSubmitDto.email)
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