package com.ourproject.gojekclone.ui.presenter

import SubmitResult
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    private val _isUserRegistered = MutableStateFlow(UserState())
    val isUserRegistered: StateFlow<UserState> = _isUserRegistered.asStateFlow()

//    private var _register: MutableLiveData<SubmitResult<Register>> = MutableLiveData()

    fun submitRegister(
        registerSubmitDto: RegisterSubmitDto
    ) {
        viewModelScope.launch {
            registerSubmit.register(
                registerSubmitDto = registerSubmitDto
            ).collect { result ->
                Log.d("TAG", "submitRegister: here your result of things is $result")
            }
        }
    }
}