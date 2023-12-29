package com.ourproject.gojekclone.ui.presenter

import SubmitResult
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.login_domain.LoginInsert
import com.ourproject.login_module.feed.http.LoginSubmitDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val loginInsert: LoginInsert
) : ViewModel() {


    private var _emailUser : MutableLiveData<String> = MutableLiveData()

    val emailUser = _emailUser
    fun login(email: String, password: String){
        viewModelScope.launch {
            loginInsert.login(com.ourproject.login_domain.LoginSubmitEntity(
                email = email,
                password = password
            )).collect{ result ->
                Log.d("TAG", "login: login operation result is $result")
//                _login.value = result.

                when(result){
                    is SubmitResult.Success -> {
                        _emailUser.postValue(email)
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