package com.example.athlitecsapp.util

data class VideoItem(
    val cover: Int,
    val title: String,
    val description: String,
    val videoResId: Int? = null,
    val image: Int? = null, // Photo location
    var isCompleted: Boolean = false // To track completion status
)