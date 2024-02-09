@file:OptIn(ExperimentalComposeUiApi::class)

package com.ourproject.gojekclone.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.ExperimentalComposeUiApi
import com.ourproject.gojekclone.R
import com.ourproject.session.usecase.LocalKey
import com.ourproject.view.DashboardActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = getSharedPreferences("email_preferences", Context.MODE_PRIVATE)

        val savedEmail = preferences.getString(LocalKey.EMAIL_SESSION, null)

        if (savedEmail != null) {
            startActivity(Intent(this, DashboardActivity::class.java))
        } else {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

}