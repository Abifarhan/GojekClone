package com.ourproject.login_module.feed.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ourproject.login_module.factories.RemoteLoginFeedLoaderFactory
import com.ourproject.login_module.feed.domain.LoginFeedLoader
import com.ourproject.login_module.feed.domain.LoginFeedResult
import com.ourproject.login_module.feed.domain.LoginSubmitEntity
import com.ourproject.login_module.feed.http.RemoteLoginFeedLoader
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.chromium.net.NetworkException

class LoginViewModel constructor(
    private val loginFeedLoader: LoginFeedLoader
) : ViewModel() {

    fun submitDataUser(submitEntity: LoginSubmitEntity) {
        viewModelScope.launch {
            try {
                loginFeedLoader.submit(submitEntity).collect{result ->
                    when (result) {
                        is LoginFeedResult.Success -> {
                            Log.d("TAG", "submitDataUser: operation resul is 1 $result")
                        }

                        is LoginFeedResult.Failure -> {
                            Log.d("TAG", "submitDataUser: operation resul is 1 $result")
                        }
                    }
                }
            } catch (e: NetworkException) {
                // Handle network-related exceptions (e.g., no connectivity)
                Log.e("TAG", "submitUserRegister: Network Exception: ${e.message}")
                // Show a message to the user indicating a network issue
            } catch (e: Exception) {
                // Handle other general exceptions
                Log.e("TAG", "submitUserRegister: Exception: ${e.message}")
                //
            }
        }
    }

    companion object {
        val FACTORY: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel(
                    RemoteLoginFeedLoaderFactory.createRemoteLoginFeedLoader()
                )
            }
        }
    }
}