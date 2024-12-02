package com.example.athlitecsapp.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.athlitecsapp.util.TimeTrialConverter

@Entity
data class Note(
    val title: String? = null,
    @TypeConverters(TimeTrialConverter::class) // Tell Room to use the TypeConverter for timeTrials
    val timeTrials: List<TimeTrial> = emptyList(),
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)