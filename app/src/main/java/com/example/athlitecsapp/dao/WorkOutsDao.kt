package com.example.athlitecsapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athlitecsapp.model.Routine
import com.example.athlitecsapp.table.Workouts

@Dao
interface WorkOutsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkouts(workouts: List<Workouts>)

    @Query("SELECT * FROM Workouts WHERE id = :id")
    suspend fun getWorkoutsById(id: Int): Workouts?

    // New method: Query workout by scope and ID
    @Query("SELECT * FROM Workouts WHERE scope = :scope AND day = :day")
    suspend fun getWorkoutsByScopeAndId(scope: String, day: Int): Workouts?
}