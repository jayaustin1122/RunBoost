package com.example.athlitecsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine")
data class Routine(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val category: String,
    val video: Int? = null ,
    val image: Int? = null ,
    var open : Boolean = false

)