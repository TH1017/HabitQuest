package com.example.habitquest.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habitquest.database.HabitDao
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import com.example.habitquest.data.HabitRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.navigation.NavController
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars

@Composable
fun HabitDetailScreen(
    habitId: Int,
    habitDao: HabitDao,
    navController: NavController
) {

    var completedToday by remember {
        mutableStateOf(false)
    }

    var records by remember {
        mutableStateOf<List<HabitRecord>>(emptyList())
    }

    var streakCount by remember {
        mutableStateOf(0)
    }

    var xp by remember {
        mutableStateOf(0)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        records = withContext(Dispatchers.IO) {
            habitDao.getRecordsByHabit(habitId)
        }
        streakCount = calculateStreak(records)

        val habit = withContext(Dispatchers.IO) {
            habitDao.getHabitById(habitId)
        }

        xp = habit?.xp ?: 0
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("← 戻る")
        }

        //Text(
            //text = "Habit ID: $habitId"
        //)

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "🔥 継続日数 ${streakCount}日"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "⭐ XP $xp"
        )

        Button(
            onClick = {

                val today = LocalDate.now().toString()

                scope.launch {

                    val existingRecord = withContext(Dispatchers.IO) {
                        habitDao.getRecordByDate(
                            habitId = habitId,
                            date = today
                        )
                    }

                    if (existingRecord == null) {

                        withContext(Dispatchers.IO) {
                            habitDao.insertRecord(
                                HabitRecord(
                                    habitId = habitId,
                                    date = today
                                )
                            )
                        }

                        withContext(Dispatchers.IO) {
                            habitDao.addXp(
                                habitId = habitId,
                                amount = 10
                            )
                        }

                        records = withContext(Dispatchers.IO) {
                            habitDao.getRecordsByHabit(habitId)
                        }
                        streakCount = calculateStreak(records)

                        completedToday = true

                        val updatedHabit = withContext(Dispatchers.IO) {
                            habitDao.getHabitById(habitId)
                        }

                        xp = updatedHabit?.xp ?: 0
                    }
                }

            }
        ) {
            Text("今日達成")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (completedToday) {
            Text("✅ 今日の達成を記録しました")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text("達成履歴")

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        records.forEach { record ->
            Text(record.date)
        }
    }
}


fun calculateStreak(records: List<HabitRecord>): Int {

    if (records.isEmpty()) return 0

    val dates = records
        .map { LocalDate.parse(it.date) }
        .toSet()

    var streak = 0
    var currentDate = LocalDate.now()

    while (dates.contains(currentDate)) {
        streak++
        currentDate = currentDate.minusDays(1)
    }

    return streak
}