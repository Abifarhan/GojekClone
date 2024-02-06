@file:OptIn(ExperimentalComposeUiApi::class)

package com.ourproject.gojekclone.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.ui.ExperimentalComposeUiApi
import com.ourproject.gojekclone.R
import com.ourproject.session.usecase.LocalKey
import com.ourproject.view.DashboardActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, LoginActivity::class.java))
//        val preferences = getSharedPreferences(LocalKey.EMAIL_SESSION, Context.MODE_PRIVATE)
//
//        val savedEmail = preferences.getString(LocalKey.EMAIL_SESSION, null)
//
//        Timber.d("here the result of your email $savedEmail")
//        Log.d("result","here the result of your email $savedEmail")
//        if (savedEmail != null) {
//        startActivity(Intent(this, DashboardActivity::class.java))
//            // Use the savedEmail as needed
//            // ...
//        } else {
//            // Handle the case where the email is not found in SharedPreferences
//            startActivity(Intent(this, RegisterActivity::class.java))
//        }

    }

}