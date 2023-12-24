package com.ourproject.register_module.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ourproject.register_module.datasource.http.dto.UserLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface RegisterDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(data: UserLocal)


    @Query("SELECT * FROM register_feed")
    fun getUserData(): Flow<UserLocal>
}