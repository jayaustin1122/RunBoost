package com.example.athlitecsapp.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val email: String,
    val password: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
)
