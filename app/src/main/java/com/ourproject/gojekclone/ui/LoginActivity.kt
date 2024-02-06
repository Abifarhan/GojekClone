
package com.ourproject.gojekclone.ui

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.ourproject.component.EmailInput
import com.ourproject.component.RoundedButton
import com.ourproject.gojekclone.ui.compose.GoFoodTheme
import com.ourproject.gojekclone.ui.presenter.LoginViewModelFactory
import com.ourproject.login_presenter.LoginViewModel
import com.ourproject.login_presenter.UserInputDataLogin
import com.ourproject.view.DashboardActivity

@ExperimentalComposeUiApi
class LoginActivity : ComponentActivity() {



    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, LoginViewModelFactory.createLoginViewModel())[LoginViewModel::class.java]

        setContent {
            GoFoodTheme {
                LoginLayout(viewModel, this@LoginActivity)
            }
        }
    }

}

@ExperimentalComposeUiApi
@Composable
fun LoginLayout(viewModel: LoginViewModel, activity: LoginActivity) {

    val context = LocalContext.current

    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit){
        viewModel.userDataLiveData.collect{
            if (it.userRegistered) {
                val intent = Intent(activity, DashboardActivity::class.java)
                activity.startActivity(intent)
            }
        }
    }

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