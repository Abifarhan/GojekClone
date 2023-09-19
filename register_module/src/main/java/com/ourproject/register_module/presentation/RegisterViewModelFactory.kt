package com.ourproject.register_module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ourproject.register_module.composite.GoFoodRegisterFactory
import com.ourproject.register_module.composite.GoFoodRegisterLoaderCacheDecorator
import com.ourproject.register_module.datasource.db.usecase.LocalRegisterFeedLoaderFactory
import com.ourproject.register_module.datasource.http.dto.RegistrationData
import com.ourproject.register_module.factory.GofoodRegisterLocalInsertFactory
import com.ourproject.register_module.factory.RemoteRegisterLoaderFactory


class RegisterViewModelFactory(private val param: RegistrationData) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterFeedViewModel::class.java)) {
            return RegisterFeedViewModel(
                GoFoodRegisterFactory.createCompositeFactory(
                    primary = GoFoodRegisterLoaderCacheDecorator(
                        decorate = RemoteRegisterLoaderFactory.createRemoteRegisterUserLoader(registrationData = param),
                        cache = GofoodRegisterLocalInsertFactory.createLocalInsertUserdata(registrationData = param)
                    ),
                    fallback = RemoteRegisterLoaderFactory.createRemoteRegisterUserLoader(registrationData = param)
                ),
                gopayResultRegisterLoader = LocalRegisterFeedLoaderFactory.createLocalCryptoRegisterFeedLoader()
            ) as T
        }
        // Call super.create(modelClass) when the modelClass is not assignable from RegisterFeedViewModel
        return super.create(modelClass) as T
    }


}