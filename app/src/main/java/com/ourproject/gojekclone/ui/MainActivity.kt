package com.ourproject.gojekclone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ourproject.gojekclone.R
import com.ourproject.register_module.RegisterFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fragment = RegisterFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()

    }
}