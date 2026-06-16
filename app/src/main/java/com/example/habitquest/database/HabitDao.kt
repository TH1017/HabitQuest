package com.example.habitquest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.habitquest.data.Habit
import com.example.habitquest.data.HabitRecord

@Dao
interface HabitDao {

    @Insert
    suspend fun insertHabit(habit: Habit)

    @Query("SELECT * FROM Habit")
    suspend fun getAllHabits(): List<Habit>

    @Insert
    suspend fun insertRecord(record: HabitRecord)

    @Query("SELECT * FROM HabitRecord WHERE habitId = :habitId")
    suspend fun getRecordsByHabit(habitId: Int): List<HabitRecord>

    @Query("""
    SELECT * FROM HabitRecord
    WHERE habitId = :habitId
    AND date = :date
    LIMIT 1
""")
    suspend fun getRecordByDate(
        habitId: Int,
        date: String
    ): HabitRecord?
}