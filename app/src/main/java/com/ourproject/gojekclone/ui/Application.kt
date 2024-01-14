package com.ourproject.gojekclone.ui

import android.app.Application
import com.ourproject.core.framework.local.LocalFactory

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        com.ourproject.core.framework.local.LocalFactory.application = this
    }
}