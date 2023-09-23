package com.ourproject.register_module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ourproject.register_module.composite.GoFoodRegisterFactory
import com.ourproject.register_module.composite.GoFoodRegisterLoaderCacheDecorator
import com.ourproject.register_module.datasource.db.usecase.LocalRegisterFeedLoaderFactory
import com.ourproject.register_module.datasource.http.dto.RegistrationData
import com.ourproject.register_module.factory.GofoodRegisterLocalInsertFactory
import com.ourproject.register_module.factory.RemoteRegisterLoaderFactory


class RegisterViewModelFactory : ViewModel() {



    companion object{
        val FACTORY : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RegisterFeedViewModel(
                    GoFoodRegisterFactory.createCompositeFactory(
                        primary = GoFoodRegisterLoaderCacheDecorator(
                            decorate = RemoteRegisterLoaderFactory.createRemoteRegisterUserLoader(),
                            cache = GofoodRegisterLocalInsertFactory.createLocalInsertUserdata()
                        ),
                        fallback = RemoteRegisterLoaderFactory.createRemoteRegisterUserLoader()
                    ),
                    gopayResultRegisterLoader = LocalRegisterFeedLoaderFactory.createLocalCryptoRegisterFeedLoader()
                )
            }
        }
    }

}