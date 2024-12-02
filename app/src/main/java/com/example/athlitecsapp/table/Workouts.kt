package com.example.athlitecsapp.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workouts(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val day: Int = 0,
    val mainWorkOut: String?=null,
    val description: String?=null,
    val purpose: String? = null,
    val tips: String? = null ,
    val scope: String? = null
)