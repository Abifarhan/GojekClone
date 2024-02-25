package com.ourproject.login_presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.login_domain.LoginSubmitDomain
import com.ourproject.login_domain.LoginUseCase
import com.ourproject.login_domain.SubmitResult
import com.ourproject.login_domain.UserSessionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LoginSubmitUiState {
    val isLoading: Boolean
    val failedMessage: String

    data class LoginSuccess(
        override val isLoading: Boolean,
        val userRegistered: Boolean,
        override val failedMessage: String
    ) : LoginSubmitUiState

    data class LoginFailed(
        override val isLoading: Boolean,
        val userRegistered: Boolean,
        override val failedMessage: String
    ) : LoginSubmitUiState

}

class LoginViewModel @Inject constructor(
    private val loginSubmitUseCase: LoginUseCase,
    private val loginSessionUseCase: UserSessionUseCase
) : ViewModel() {


    private val viewModelState = MutableStateFlow(
        LoginViewModelState(
            isLoading = true,
            failedMessage = "",
            isLoginSuccess = false
        )
    )

    var loginUiState = viewModelState
        .map(LoginViewModelState::toLoginUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = viewModelState.value.toLoginUiState()
        )

    fun login(inputLoginForm: UserInputDataLogin) {
    viewModelScope.launch {

        loginSubmitUseCase.login(
            LoginSubmitDomain(
                email = inputLoginForm.email,
                password = inputLoginForm.password
            )
        ).collect { result ->

            viewModelState.update {
                when(result){
                    is SubmitResult.Success -> {
                        loginSessionUseCase.insertUserSession(result.data)
                        it.copy(
                            isLoginSuccess = true,
                            isLoading = false,
                            failedMessage = ""
                        )
                    }

                    is SubmitResult.Failure -> it.copy(
                        isLoginSuccess = false,
                        isLoading = false,
                        failedMessage = "failed login "
                    )
                }
            }
        }
    }
    }
}


data class LoginViewModelState(
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val failedMessage: String
) {

    fun toLoginUiState(): LoginSubmitUiState {
        return if (isLoginSuccess) LoginSubmitUiState.LoginSuccess(
            isLoading = isLoading,
            userRegistered = isLoginSuccess,
            failedMessage = failedMessage
        ) else
            LoginSubmitUiState.LoginSuccess(
                isLoading = isLoading,
                userRegistered = isLoginSuccess,
                failedMessage = failedMessage
            )
    }

}