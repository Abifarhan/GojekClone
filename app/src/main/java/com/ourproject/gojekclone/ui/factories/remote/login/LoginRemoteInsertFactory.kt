package com.ourproject.gojekclone.ui.factories.remote.login

import com.ourproject.login_domain.LoginUseCase
import com.ourproject.login_http.RemoteLoginUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class LoginRemoteInsertFactory {

   @Binds
   abstract fun createRemoteLoginUseCase(
      remoteLoginUseCase: RemoteLoginUseCase
   ) : LoginUseCase
}