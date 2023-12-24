package com.ourproject.gojekclone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ourproject.gojekclone.R
import com.ourproject.register_module.ui.RegisterActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




//        startActivity(Intent(this, LoginActivity::class.java))
        startActivity(Intent(this, RegisterActivity::class.java))


    }

}