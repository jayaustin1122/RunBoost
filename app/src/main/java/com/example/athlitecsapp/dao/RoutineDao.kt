package com.example.athlitecsapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athlitecsapp.model.Routine

@Dao
interface RoutineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTinikling(tinikling: List<Routine>)

    @Query("SELECT * FROM routine WHERE category = :category")
    suspend fun getTiniklingByCategory(category: String): List<Routine>
    @Query("SELECT * FROM routine")
    suspend fun getAllTinikling(): List<Routine>
    @Query("SELECT * FROM routine WHERE video IS NOT NULL")
    suspend fun getTiniklingWithVideo(): List<Routine>
}