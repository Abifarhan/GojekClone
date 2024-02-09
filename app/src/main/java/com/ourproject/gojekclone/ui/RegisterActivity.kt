package com.ourproject.gojekclone.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.ourproject.component.EmailInput
import com.ourproject.gojekclone.ui.compose.GoFoodTheme
import com.ourproject.register_presenter.RegisterViewModel
import com.ourproject.gojekclone.ui.presenter.RegisterViewModelFactory
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ourproject.component.RoundedButton
import com.ourproject.component.showToast
import com.ourproject.register_domain.SubmitResult
import com.ourproject.register_presenter.UserInputData
import com.ourproject.view.DashboardActivity
import timber.log.Timber

@ExperimentalComposeUiApi
class RegisterActivity : ComponentActivity() {

    private lateinit var viewModel: RegisterViewModel
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, RegisterViewModelFactory.createRegisterViewModelFactory())[RegisterViewModel::class.java]


            setContent {
                GoFoodTheme {
                    RegisterLayout(viewModel = viewModel, this@RegisterActivity)
                }
            }

    }

}


@ExperimentalComposeUiApi
@Composable
fun RegisterLayout(
    viewModel : RegisterViewModel,
    activity: RegisterActivity
){
    val context = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.isUserRegistered.collect{
            if (it.userRegistered){
                val intent = Intent(activity, DashboardActivity::class.java)
                activity.startActivity(intent)
            }
        }
    }


    val email = rememberSaveable{ mutableStateOf("") }
    val password = rememberSaveable{ mutableStateOf("") }
    val name = rememberSaveable{ mutableStateOf("") }
    val phoneNumber = rememberSaveable{ mutableStateOf("") }
    val houseNumber = rememberSaveable{ mutableStateOf("") }
    val address = rememberSaveable{ mutableStateOf("") }
    val city = rememberSaveable{ mutableStateOf("") }
    val passwordFocusRequest = FocusRequester.Default


    Surface(color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Column(
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ){

                    EmailInput(
                        emailState = name, enabled = true,
                        labelId = "Name",
                        onAction = KeyboardActions{
                            passwordFocusRequest.requestFocus()
                        }
                    )

                    EmailInput(
                        emailState = email, enabled = true,
                        onAction = KeyboardActions{
                            passwordFocusRequest.requestFocus()
                        }
                    )

                    EmailInput(
                        emailState = password, enabled = true,
                        labelId = "Password",
                        onAction = KeyboardActions{
                            passwordFocusRequest.requestFocus()
                        }
                    )

                    EmailInput(
                        emailState = phoneNumber, enabled = true,
                        labelId = "Nomor HP",
                        onAction = KeyboardActions{
                            passwordFocusRequest.requestFocus()
                        }
                    )

                    EmailInput(
                        emailState = address, enabled = true,
                        labelId = "Address",
                        onAction = KeyboardActions{
                            passwordFocusRequest.requestFocus()
                        }
                    )

                    EmailInput(
                        emailState = address, enabled = true,
                        labelId = "City",
                        onAction = KeyboardActions{
                            passwordFocusRequest.requestFocus()
                        }
                    )

                    EmailInput(
                        emailState = houseNumber, enabled = true,
                        labelId = "Nomor Rumah",
                        onAction = KeyboardActions{
                            passwordFocusRequest.requestFocus()
                        }
                    )

                    Row(modifier = Modifier.padding(top = 6.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        RoundedButton(label = "Save",

                            onPress = {

                                showToast(context = context, "${email.value}")
                                viewModel.submitRegister(
                                    registerSubmitData = UserInputData(
                                        name = name.value,
                                        email = email.value,
                                        password = password.value,
                                        password_confirmation = password.value,
                                        address = address.value,
                                        city = city.value,
                                        houseNumber = houseNumber.value,
                                        phoneNumber = phoneNumber.value
                                    )
                                )
                            }
                            )

                    }
                }
            }

        }
        )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoFoodTheme {

    }
}