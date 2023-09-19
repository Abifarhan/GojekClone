package com.ourproject.gojekclone.ui

import android.app.Application
import com.ourproject.register_module.frameworks.LocalFactory

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        LocalFactory.application = this
    }
}