package com.ourproject.login_module.feed.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LoginDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(data: UserLocal)

    @Query("SELECT * FROM register_feed")
    fun getUserData(): Flow<UserLocal>
}