package com.ourproject.gojekclone.ui

import android.app.Application
import com.ourproject.core.framework.local.LocalFactory
import dagger.hilt.android.HiltAndroidApp

class Application : Application() {

    val mainComponent: MainComponent by lazy{

    }
}