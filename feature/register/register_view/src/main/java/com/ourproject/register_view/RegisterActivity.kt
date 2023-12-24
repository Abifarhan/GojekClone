package com.ourproject.register_module.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ourproject.register_view.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val fragment = RegisterFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.register_container, fragment)
            .commit()
    }
}