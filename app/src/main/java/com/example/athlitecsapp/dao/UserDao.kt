package com.example.athlitecsapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.athlitecsapp.table.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User LIMIT 1 ")
    suspend fun getSingleUser(): User?

}