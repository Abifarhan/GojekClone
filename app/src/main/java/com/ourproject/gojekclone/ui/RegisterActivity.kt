package com.ourproject.gojekclone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ourproject.gojekclone.R
import com.ourproject.gojekclone.ui.presenter.RegisterViewModel
import com.ourproject.gojekclone.ui.presenter.RegisterViewModelFactory
import com.ourproject.register_domain.api.RegisterSubmitDto

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProvider(this, RegisterViewModelFactory.createRegisterViewModelFactory())[RegisterViewModel::class.java]

        viewModel.submitRegister(
            registerSubmitDto = RegisterSubmitDto(
                name = "hahhaa",
                    email = "gelas26@gmail.com",
                    password = "1234567890",
                    password_confirmation = "1234567890",
                    address = "berlin",
                    city = "berlin",
                    houseNumber = "4",
                    phoneNumber = "1234567890"
            )
        )
    }
}