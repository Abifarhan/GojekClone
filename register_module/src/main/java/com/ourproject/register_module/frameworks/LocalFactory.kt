package com.ourproject.register_module.frameworks

import android.app.Application
import androidx.room.Room
import com.ourproject.register_module.datasource.db.GofoodDatabase

object LocalFactory {

    lateinit var application: Application

    fun createDatabase() = Room.databaseBuilder(
        application.applicationContext,
        GofoodDatabase::class.java,
        "gofood_database"
    ).build()
}