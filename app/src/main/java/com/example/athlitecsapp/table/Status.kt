package com.example.athlitecsapp.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Status(
    val state: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
)