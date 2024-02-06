@file:OptIn(ExperimentalComposeUiApi::class)

package com.ourproject.gojekclone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ourproject.gojekclone.R
import com.ourproject.gojekclone.ui.presenter.LoginViewModelFactory
import com.ourproject.view.DashboardActivity

class LoginActivity : AppCompatActivity() {



    private lateinit var viewModel: com.ourproject.login_presenter.LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        viewModel = ViewModelProvider(this, LoginViewModelFactory.createLoginViewModel())[com.ourproject.login_presenter.LoginViewModel::class.java]


//        setListener()
//        setObserver()
    }

//    private fun setObserver() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.userDataLiveData.collect { userState ->
//                if (userState.userRegistered) {
//                    val intent =  Intent(this@LoginActivity, DashboardActivity::class.java)
//                    startActivity(intent)
//                }
//
//            }
//        }
//    }
//
//    private fun setListener() {
//        editTextEmail = findViewById(R.id.email)
//        editTextPassword = findViewById(R.id.password)
//        btnLogin = findViewById(R.id.btnLogin)
//        btnRegister = findViewById(R.id.btnRegister)
//
//
//        btnLogin.setOnClickListener {
//
//            viewModel.login(
//                email = editTextEmail.text.toString().trim(),
//                password = editTextPassword.text.toString().trim()
//            )
////            viewModel.login(
////                email = "andika@gmail.com",
////                password = "1234567890"
////            )
//        }
//
//        btnRegister.setOnClickListener {
//            val intent =  Intent(this@LoginActivity, RegisterActivity::class.java)
//            startActivity(intent)
//        }
//    }
}