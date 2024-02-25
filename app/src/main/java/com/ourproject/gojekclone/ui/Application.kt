package com.ourproject.gojekclone.ui

import android.app.Application
import com.ourproject.core.framework.local.AppModule
import com.ourproject.core.framework.local.LocalFactory
import com.ourproject.gojekclone.ui.di.DaggerMainComponent
import com.ourproject.gojekclone.ui.di.MainComponent

class Application : Application() {
    val mainComponent: MainComponent by lazy {
        DaggerMainComponent.factory()
            .application(this)
            .create()
    }
}