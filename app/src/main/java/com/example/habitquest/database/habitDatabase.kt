package com.example.habitquest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habitquest.data.Habit
import com.example.habitquest.data.HabitRecord

@Database(
    entities = [Habit::class, HabitRecord::class],
    version = 2
)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao
}