package com.example.athlitecsapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athlitecsapp.model.Routine

@Dao
interface RoutineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: List<Routine>)

    @Query("SELECT * FROM routine WHERE category = :category")
    suspend fun getRoutineByCategory(category: String): List<Routine>
    @Query("SELECT * FROM routine")
    suspend fun getAllRoutines(): List<Routine>
    @Query("SELECT * FROM routine")
    suspend fun getRoutineWithVideo(): List<Routine>

    @Query("SELECT * FROM routine WHERE id = :id")
    suspend fun getRoutineById(id: Int): Routine


}