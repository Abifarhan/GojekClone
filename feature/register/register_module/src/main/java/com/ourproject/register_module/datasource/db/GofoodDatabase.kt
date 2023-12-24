package com.ourproject.register_module.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ourproject.register_module.datasource.http.dto.UserLocal


@Database(entities = [UserLocal::class], version = 1)
abstract class GofoodDatabase : RoomDatabase() {
    abstract fun registerDao(): RegisterDao

}