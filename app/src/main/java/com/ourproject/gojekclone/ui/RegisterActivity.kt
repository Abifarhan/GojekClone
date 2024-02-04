package com.ourproject.gojekclone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ourproject.component.header.HeaderWithTitle
import com.ourproject.gojekclone.R
import com.ourproject.register_presenter.RegisterViewModel
import com.ourproject.gojekclone.ui.presenter.RegisterViewModelFactory
import com.ourproject.register_domain.api.RegisterSubmitEntity
import com.ourproject.register_http.usecase.dto.RegisterSubmitDto
import com.ourproject.view.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    @Inject
    lateinit var

    private lateinit var frameLayout: LinearLayout
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var moveButton: Button

    private lateinit var secondLinearLayout: LinearLayout
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var houseNumberEditText: EditText
    private lateinit var spinner: Spinner
    private lateinit var executeButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProvider(this, RegisterViewModelFactory.createRegisterViewModelFactory())[RegisterViewModel::class.java]


        frameLayout = findViewById(R.id.frameLayout)
        nameEditText = findViewById(R.id.name)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        moveButton = findViewById(R.id.btn_move)

        secondLinearLayout = findViewById(R.id.secondLinearLayout)
        phoneEditText = findViewById(R.id.phone)
        addressEditText = findViewById(R.id.address)
        houseNumberEditText = findViewById(R.id.houseNumber)
        spinner = findViewById(R.id.spinner)
        executeButton = findViewById(R.id.btn_execute)

        val data1 = arrayOf("Jakarta", "Bandung", "Tangerang")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            data1
        )
        spinner.adapter = adapter

        setListener()
        setObserver()
    }

    private fun setObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.isUserRegistered.collect{userState ->
                if (userState.userRegistered) {
                    val intent = Intent(this@RegisterActivity, DashboardActivity::class.java)
                    startActivity(intent)
                }

            }
        }
    }

    private fun setListener() {
        moveButton.apply {
            setOnClickListener {
                secondLinearLayout.visibility = View.VISIBLE
                this.visibility = View.GONE
            }
        }

        executeButton.apply {
            setOnClickListener {

                viewModel.submitRegister(
                    registerSubmitData = RegisterSubmitEntity(
                        name = nameEditText.text.toString().trim(),
                        email = emailEditText.text.toString().trim(),
                        password = passwordEditText.text.toString().trim(),
                        password_confirmation = passwordEditText.text.toString().trim(),
                        address = addressEditText.text.toString(),
                        city = spinner.selectedItem.toString(),
                        houseNumber = houseNumberEditText.text.toString().trim(),
                        phoneNumber = phoneEditText.text.toString().trim()
                    )
                )
//                viewModel.submitRegister(
//                    registerSubmitData = RegisterSubmitEntity(
//                        name = "hahhaa",
//                        email = "gelas40@gmail.com",
//                        password = "1234567890",
//                        password_confirmation = "1234567890",
//                        address = "berlin",
//                        city = "berlin",
//                        houseNumber = "4",
//                        phoneNumber = "1234567890"
//                    )
//                )
            }
        }
    }
}