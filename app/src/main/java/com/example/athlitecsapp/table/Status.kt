package com.example.athlitecsapp.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Status(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val level1: Int = 0,
    val level2: Int = 0,
    val level3: Int = 0,
    val level4: Int = 0,
    val level5: Int = 0,
    val level6: Int = 0,
    val level7: Int = 0,
    val level1DayProgress: Int = 0,
    val level2DayProgress: Int = 0,
    val level3DayProgress: Int = 0,
    val level4DayProgress: Int = 0,
    val level5DayProgress: Int = 0,
    val level6DayProgress: Int = 0,
    val level7DayProgress: Int = 0,
)