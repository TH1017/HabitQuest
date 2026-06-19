package com.example.habitquest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val xp: Int = 0
)