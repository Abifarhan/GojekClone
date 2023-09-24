package com.ourproject.gojekclone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ourproject.feature_dashboard.DashboardActivity
import com.ourproject.gojekclone.R
import com.ourproject.login_module.feed.ui.LoginActivity
import com.ourproject.register_module.ui.RegisterActivity
import com.ourproject.session_module.SessionManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        SessionManager.init(this)
        val checkSession = SessionManager.retrieveUserData()

        Log.d("TAG", "onViewCreated: user data you have is $checkSession")

        startActivity(Intent(this, LoginActivity::class.java))


    }

}