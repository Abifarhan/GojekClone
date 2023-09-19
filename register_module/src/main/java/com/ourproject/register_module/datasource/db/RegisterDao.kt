package com.ourproject.register_module.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ourproject.register_module.datasource.http.dto.UserLocal

@Dao
interface RegisterDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(data: UserLocal)
}