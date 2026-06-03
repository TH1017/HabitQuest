package com.example.habitquest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HabitRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val habitId: Int,

    val date: String
)