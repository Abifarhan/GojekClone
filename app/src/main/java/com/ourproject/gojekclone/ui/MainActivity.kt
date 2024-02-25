@file:OptIn(ExperimentalComposeUiApi::class)

package com.ourproject.gojekclone.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.ExperimentalComposeUiApi
import com.ourproject.gojekclone.R
import com.ourproject.session.usecase.LocalKey
import com.ourproject.session.usecase.LoginPreferenceClient
import com.ourproject.view.DashboardActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: LoginPreferenceClient

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as Application).mainComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val preferences = getSharedPreferences("email_preferences", Context.MODE_PRIVATE)
//
//        val savedEmail = preferences.getString(LocalKey.EMAIL_SESSION, null)
//
//        if (savedEmail != null) {
//            startActivity(Intent(this, DashboardActivity::class.java))
//        } else {
            startActivity(Intent(this, LoginActivity::class.java))
//        }

    }

}