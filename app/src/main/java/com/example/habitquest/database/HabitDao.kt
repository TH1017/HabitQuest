package com.example.habitquest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.habitquest.data.Habit
import com.example.habitquest.data.HabitRecord
import androidx.room.Delete

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

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("DELETE FROM HabitRecord WHERE habitId = :habitId")
    suspend fun deleteRecordsByHabit(habitId: Int)

    @Query("""
    UPDATE Habit
    SET xp = xp + :amount
    WHERE id = :habitId
""")
    suspend fun addXp(
        habitId: Int,
        amount: Int
    )

    @Query("""
    SELECT * FROM Habit
    WHERE id = :habitId
    LIMIT 1
""")
    suspend fun getHabitById(
        habitId: Int
    ): Habit?
}