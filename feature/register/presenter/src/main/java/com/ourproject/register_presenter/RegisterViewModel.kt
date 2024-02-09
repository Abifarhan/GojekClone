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

            registerSubmit.register(
                registerSubmitDomain = RegisterSubmitDomain(
                    name = registerSubmitData.name,
                    email = registerSubmitData.email,
                    password = registerSubmitData.password,
                    password_confirmation = registerSubmitData.password_confirmation,
                    address = registerSubmitData.address,
                    city = registerSubmitData.city,
                    houseNumber = registerSubmitData.houseNumber,
                    phoneNumber = registerSubmitData.phoneNumber
                )
            ).collect { result ->

                _isUserRegistered.update {
                    when (result) {
                        is SubmitResult.Success -> {
                            Log.d("TAG", "submitRegister: here the result of operation success")
                            it.copy(
                                isLoading = false,
                                userRegistered = true
                            )
                        }

                        is SubmitResult.Failure -> {
                            Log.d("TAG", "submitRegister: here the result of operation failure ${result.errorMessage}")
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

    fun submitRegisterVersion2(registerSubmitData:  UserInputData) = runBlocking {
        registerSubmit.register(
        registerSubmitDomain = RegisterSubmitDomain(
            name = registerSubmitData.name,
            email = registerSubmitData.email,
            password = registerSubmitData.password,
            password_confirmation = registerSubmitData.password_confirmation,
            address = registerSubmitData.address,
            city = registerSubmitData.city,
            houseNumber = registerSubmitData.houseNumber,
            phoneNumber = registerSubmitData.phoneNumber
        )
        )
    }
}