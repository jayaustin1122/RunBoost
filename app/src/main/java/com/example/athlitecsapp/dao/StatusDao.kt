package com.example.athlitecsapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.athlitecsapp.table.Status

@Dao
interface StatusDao {
    @Insert
    suspend fun insertStatus(statusList: Status)

    @Query("SELECT * FROM Status WHERE id = :id")
    suspend fun getStatusById(id: Long): Status?

    // Add more query methods as needed
    @Query("SELECT * FROM Status")
    suspend fun getAllStatus(): Status

    @Query("""
    UPDATE Status 
    SET level1 = :level1, 
        level1DayProgress = :level1DayProgress 
    WHERE id = :id
    """)
    suspend fun updateLevel1AndProgress(id: Int, level1: Int, level1DayProgress: Int)
    @Query("""
    UPDATE Status 
    SET 
        level2 = :level2, 
        level2DayProgress = :level2DayProgress
    WHERE id = :id
    """)
    suspend fun updateLevel2AndProgress(id: Int, level2: Int, level2DayProgress: Int)
    @Query("""
    UPDATE Status 
    SET 
        level3 = :level3, 
        level3DayProgress = :level3DayProgress
    WHERE id = :id
    """)
    suspend fun updateLevel3AndProgress(id: Int, level3: Int, level3DayProgress: Int)

    @Query("""UPDATE Status SET level3DayProgress = :level3DayProgress""")
    suspend fun updateLevel3DayProgressForAll(level3DayProgress: Int)

    @Query("""UPDATE Status SET level4DayProgress = :level4DayProgress""")
    suspend fun updateLevel4DayProgressForAll(level4DayProgress: Int)

    @Query("""UPDATE Status SET level5DayProgress = :level5DayProgress""")
    suspend fun updateLevel5DayProgressForAll(level5DayProgress: Int)

    @Query("""UPDATE Status SET level6DayProgress = :level6DayProgress""")
    suspend fun updateLevel6DayProgressForAll(level6DayProgress: Int)

    @Query("""UPDATE Status SET level7DayProgress = :level7DayProgress""")
    suspend fun updateLevel7DayProgressForAll(level7DayProgress: Int)

}