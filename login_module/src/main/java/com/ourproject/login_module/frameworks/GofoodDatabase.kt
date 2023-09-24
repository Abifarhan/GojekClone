package com.ourproject.login_module.frameworks

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ourproject.login_module.feed.db.LoginDao
import com.ourproject.login_module.feed.db.UserLocal

@Database(entities = [UserLocal::class], version = 1)
abstract class GofoodDatabase : RoomDatabase() {
    abstract fun loginDao(): LoginDao

}