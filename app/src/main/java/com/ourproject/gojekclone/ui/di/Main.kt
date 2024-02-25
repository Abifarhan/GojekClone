package com.ourproject.gojekclone.ui.di

import com.ourproject.core.framework.local.AppModule
import com.ourproject.core.framework.local.LocalFactory
import com.ourproject.core.framework.remote.HttpFactory
import com.ourproject.gojekclone.ui.MainActivity
import com.ourproject.gojekclone.ui.factories.local.login.LocalLoginSessionFactory
import com.ourproject.gojekclone.ui.factories.local.login.LocalLoginSessionInsertFactory
import com.ourproject.gojekclone.ui.factories.remote.login.LoginHttpClientFactory
import com.ourproject.gojekclone.ui.factories.remote.login.LoginRemoteInsertFactory
import com.ourproject.gojekclone.ui.factories.remote.login.LoginServiceFactory
import com.ourproject.infrastructure.remote.LoginRetrofitClient
import com.ourproject.infrastructure.session.UserLoginSessionPreferenceClient
import com.ourproject.login_http.RemoteLoginUseCase
import com.ourproject.login_presenter.LoginViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        LoginRemoteInsertFactory::class,
        LoginHttpClientFactory::class,
        LoginServiceFactory::class,
        HttpFactory::class,
        LocalLoginSessionInsertFactory::class,
        LocalFactory::class,
        LocalLoginSessionFactory::class,
    AppModule::class
    ]
)
interface MainComponent{

    @Component.Factory
    interface Factory {

        @BindsInstance
        fun application(application: android.app.Application): Factory
        fun create(): MainComponent

    }

    fun loginViewModel(): LoginViewModel

    fun inject(activity: MainActivity)
}