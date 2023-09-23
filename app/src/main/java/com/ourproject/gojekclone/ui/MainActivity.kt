package com.ourproject.gojekclone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ourproject.feature_dashboard.DashboardActivity
import com.ourproject.gojekclone.R
import com.ourproject.register_module.ui.RegisterActivity
import com.ourproject.session_module.SessionManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // todo check if user data already exist

        SessionManager.init(this)
        val checkSession = SessionManager.retrieveUserData()

        Log.d("TAG", "onViewCreated: user data you have is $checkSession")

        if (checkSession != null) {
            Log.d("TAG", "onViewCreated: user data you have is 1")
                startActivity( Intent(this, DashboardActivity::class.java))
        } else {
            Log.d("TAG", "onViewCreated: user data you have is 2")
            startActivity(Intent(this, RegisterActivity::class.java))

        }


    }

}