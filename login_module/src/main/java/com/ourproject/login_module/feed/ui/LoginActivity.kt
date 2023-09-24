package com.ourproject.login_module.feed.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ourproject.feature_dashboard.DashboardActivity
import com.ourproject.login_module.R
import com.ourproject.login_module.feed.domain.LoginSubmitEntity
import com.ourproject.login_module.feed.presentation.LoginViewModel
import com.ourproject.register_module.ui.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       viewModel = ViewModelProvider(this, LoginViewModel.FACTORY).get(LoginViewModel::class.java)

//        viewModel.checkSession()
//
//        viewModel.userDataLiveData.observe(this){userData ->
//
//            if (userData != null) {
//                val intent = Intent(this, DashboardActivity::class.java)
//                startActivity(intent)
//            }
//        }


        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)

        val loginButton = findViewById<Button>(R.id.btnLogin)
        val registerButton = findViewById<Button>(R.id.btnRegister)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
//            val submitEntity = LoginSubmitEntity(email,password)
            val submitEntity = LoginSubmitEntity("fandi5@gmail.com","1234567890")

            viewModel.submitDataUser(submitEntity)

            viewModel.loginStatus.observe(this, {isSuccess ->
                if (isSuccess) {
                    startActivity(Intent(this, DashboardActivity::class.java))
                }
            })
        }

        registerButton.setOnClickListener {

            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}