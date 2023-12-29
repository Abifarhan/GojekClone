package com.ourproject.gojekclone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.ourproject.gojekclone.R
import com.ourproject.gojekclone.ui.presenter.LoginViewModel
import com.ourproject.gojekclone.ui.presenter.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {


    private lateinit var txtLogin: TextView
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        viewModel = ViewModelProvider(this, LoginViewModelFactory.createLoginViewModel())[LoginViewModel::class.java]




        setListener()
        setObserver()
    }

    private fun setObserver() {
        viewModel.emailUser.observe(this) {
            if (it.isEmpty()) {
                startActivity(Intent(this, RegisterActivity::class.java))
            } else {
//                startActivity(Intent(this, DashboardActivity::class.java))
            }
        }
    }

    private fun setListener() {
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)


        btnLogin.setOnClickListener {

            viewModel.login(
                email = editTextEmail.text.toString().trim(),
                password = editTextPassword.text.toString().trim()
            )
//            viewModel.login(
//                email = "andika@gmail.com",
//                password = "1234567890"
//            )
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}