package com.ourproject.gojekclone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ourproject.gojekclone.R
import com.ourproject.gojekclone.ui.presenter.LoginViewModel
import com.ourproject.gojekclone.ui.presenter.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        viewModel = ViewModelProvider(this, LoginViewModelFactory.createLoginViewModel())[LoginViewModel::class.java]


        viewModel.login(
            email = "andika@gmail.com",
            password = "1234567890"
        )
    }
}