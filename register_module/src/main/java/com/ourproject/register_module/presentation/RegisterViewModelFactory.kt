package com.ourproject.register_module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ourproject.register_module.composite.RegisterFeedLoaderFactory
import com.ourproject.register_module.composite.GoFoodRegisterLoaderSessionDecorator
import com.ourproject.register_module.datasource.db.usecase.LocalRegisterFeedLoaderFactory
import com.ourproject.register_module.factory.GofoodRegisterLocalInsertFactory
import com.ourproject.register_module.factory.RemoteRegisterLoaderFactory


class RegisterViewModelFactory : ViewModel() {



    companion object{
        val FACTORY : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RegisterFeedViewModel(
                    RegisterFeedLoaderFactory.createCompositeFactory(
                        primary = GoFoodRegisterLoaderSessionDecorator(
                            decorate = RemoteRegisterLoaderFactory.createRemoteRegisterUserLoader(),
                            session = GofoodRegisterLocalInsertFactory.createLocalInsertUserdata()
                        ),
                        fallback = RemoteRegisterLoaderFactory.createRemoteRegisterUserLoader()
                    ),
                    gopayResultRegisterLoader = LocalRegisterFeedLoaderFactory.createLocalCryptoRegisterFeedLoader()
                )
            }
        }
    }

}