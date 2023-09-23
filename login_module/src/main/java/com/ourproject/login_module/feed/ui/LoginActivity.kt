package com.ourproject.login_module.feed.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ourproject.login_module.R
import com.ourproject.login_module.feed.domain.LoginSubmitEntity
import com.ourproject.login_module.feed.presentation.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Toast.makeText(this, "here we go", Toast.LENGTH_SHORT).show()

       viewModel = ViewModelProvider(this, LoginViewModel.FACTORY).get(LoginViewModel::class.java)

        val submitEntity = LoginSubmitEntity("fonso47@gmail.com","1234567890")

        viewModel.submitDataUser(submitEntity)


    }
}