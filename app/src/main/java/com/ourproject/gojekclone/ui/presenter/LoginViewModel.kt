package com.ourproject.gojekclone.ui.presenter

import SubmitResult
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.login_domain.LoginInsert
import com.ourproject.login_module.feed.http.LoginSubmitDto
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val loginInsert: LoginInsert
) : ViewModel() {

    private var _loginStatus: MutableLiveData<SubmitResult<LoginSubmitDto>> = MutableLiveData()
    val loginStatus get() = _loginStatus
    fun login(email: String, password: String){
        viewModelScope.launch {
            loginInsert.login(com.ourproject.login_domain.LoginSubmitEntity(
                email = email,
                password = password
            )).collect{ result ->
                Log.d("TAG", "login: login operation result is $result")
//                _login.value = result.
            }
        }
    }
}