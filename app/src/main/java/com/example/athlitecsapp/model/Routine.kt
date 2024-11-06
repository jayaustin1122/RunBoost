package com.example.athlitecsapp.model

data class Routine(
    val title: String,
    val description: String,
    val videoId: Int,
    val id: Int,
    var isExpandable  : Boolean = false,
    var isPlayed : Boolean = false,
    var open : Boolean = false

)