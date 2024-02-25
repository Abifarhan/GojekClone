package com.ourproject.register_presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.register_domain.SubmitResult
import com.ourproject.register_domain.RegisterUseCase
import com.ourproject.register_domain.RegisterSubmitDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class UserState(
    val isLoading: Boolean = false,
    val failedMessage: String = "",
    val userRegistered: Boolean = false
)
class RegisterViewModel constructor(
    private val registerSubmit: RegisterUseCase
): ViewModel(){
//
    private val _isUserRegistered = MutableStateFlow(UserState())
    val isUserRegistered: StateFlow<UserState> = _isUserRegistered.asStateFlow()

    fun submitRegister(
        registerSubmitData:  UserInputData
    ) {
        viewModelScope.launch {

            _isUserRegistered.update {
                it.copy(isLoading = true)
            }

            registerSubmit.register(registerSubmitData.presenterToDomain()).collect { result ->

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

            }
        }
    }


    private fun UserInputData.presenterToDomain() = RegisterSubmitDomain(
        name = this.name,
        email = this.email,
        password = this.password,
        password_confirmation = this.password_confirmation,
        address = this.address,
        city = this.city,
        houseNumber = this.houseNumber,
        phoneNumber = this.phoneNumber
    )
}