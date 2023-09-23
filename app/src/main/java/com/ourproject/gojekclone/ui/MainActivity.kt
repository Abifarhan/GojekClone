package com.ourproject.gojekclone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ourproject.feature_dashboard.DashboardActivity
import com.ourproject.gojekclone.R
import com.ourproject.login_module.feed.ui.LoginActivity
import com.ourproject.register_module.ui.RegisterFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fragment = RegisterFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()


        startActivity(Intent(this, LoginActivity::class.java))

    }

}