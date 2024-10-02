package com.example.athlitecsapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.athlitecsapp.table.Status

@Dao
interface StatusDao {
    @Insert
    suspend fun insertStatus(statusList: List<Status>)

    @Query("UPDATE Status SET state = :newStatus WHERE id = :id")
    suspend fun updateStatusById(id: Long, newStatus: Boolean)
    @Query("SELECT * FROM Status WHERE id = :id")
    suspend fun getStatusById(id: Long): Status?

    // Add more query methods as needed
    @Query("SELECT * FROM Status")
    suspend fun getAllStatus(): List<Status>
}