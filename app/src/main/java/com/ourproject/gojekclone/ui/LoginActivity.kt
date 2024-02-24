
package com.ourproject.gojekclone.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ourproject.component.EmailInput
import com.ourproject.component.RoundedButton
import com.ourproject.gojekclone.ui.compose.GoFoodTheme
import com.ourproject.gojekclone.ui.di.MainComponent
import com.ourproject.gojekclone.ui.presenter.LoginViewModelFactory
import com.ourproject.login_presenter.LoginViewModel
import com.ourproject.login_presenter.UserInputDataLogin
import com.ourproject.view.DashboardActivity

@ExperimentalComposeUiApi
class LoginActivity : ComponentActivity() {



    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainComponent = (application as Application).mainComponent

        setContent {
            GoFoodTheme {
                LoginLayout(mainComponent = mainComponent)
            }
        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun LoginLayout(mainComponent: MainComponent) {

    val context = LocalContext.current

    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    val viewModel: LoginViewModel = viewModel {
        mainComponent.loginViewModel()
    }

    val userStateLogin by viewModel.loginUiState.collectAsStateWithLifecycle()
    Log.d("loadInsert", "$userStateLogin")

    viewModel.login(
        UserInputDataLogin(
            email = "birin1@gmail.com",
            password =  "123456789"
        )
    )
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(),
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colors.surface)
                .border(
                    1.dp,
                    MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(8.dp)
                )
            ){
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    EmailInput(emailState = email, enabled = true, labelId = "Email")
                    EmailInput(emailState = password, enabled = true, labelId = "Password")

                    RoundedButton(label = "Submit",

                        onPress = {
                            viewModel.login(
                                UserInputDataLogin(
                                    email = email.value,
                                    password =  password.value
                                )
                            )
                        }
                    )
                }

            }
        }
        )

}