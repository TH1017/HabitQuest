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

@Composable
fun HabitDetailScreen(
    habitId: Int,
    habitDao: HabitDao
) {

    var completedToday by remember {
        mutableStateOf(false)
    }

    var records by remember {
        mutableStateOf<List<HabitRecord>>(emptyList())
    }

    val scope = rememberCoroutineScope()

    //LaunchedEffect(Unit) {
    //    records = withContext(Dispatchers.IO) {
    //        habitDao.getRecordsByHabit(habitId)
    //    }
    //}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Habit ID: $habitId"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "🔥 継続日数 0日"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {

                val today = LocalDate.now().toString()

                scope.launch {

                    habitDao.insertRecord(
                        HabitRecord(
                            habitId = habitId,
                            date = today
                        )
                    )
                    records = withContext(Dispatchers.IO) {
                        habitDao.getRecordsByHabit(habitId)
                    }
                }

                completedToday = true
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