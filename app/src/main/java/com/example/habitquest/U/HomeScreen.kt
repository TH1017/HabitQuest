package com.example.habitquest.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habitquest.database.HabitDao
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import com.example.habitquest.data.Habit
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.habitquest.R

@Composable
fun HomeScreen(
    habitDao: HabitDao,
    navController: NavController
) {

    var habitName by remember {
        mutableStateOf("")
    }

    var totalXp by remember {
        mutableStateOf(0)
    }

    var title by remember {
        mutableStateOf("👶 習慣初心者")
    }

    val habits = remember {
        mutableStateListOf<Habit>()
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

        val savedHabits = withContext(Dispatchers.IO) {
            habitDao.getAllHabits()
        }

        habits.clear()

        habits.addAll(savedHabits)

        totalXp = savedHabits.sumOf { it.xp }

        title = calculateTitle(totalXp)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(" ")
        Image(
            painter = painterResource(
                id = R.drawable.habitquest_logo
            ),
            contentDescription = "HabitQuest Logo",
                    modifier = Modifier
                    .fillMaxWidth()
                .height(140.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title
        )

        Text(
            text = "⭐ 総XP $totalXp"
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = habitName,
            onValueChange = {
                habitName = it
            },
            label = {
                Text("習慣名")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (habitName.isNotBlank()) {

                    val newHabit = Habit(
                        name = habitName
                    )

                    habits.add(newHabit)

                    scope.launch {
                        habitDao.insertHabit(newHabit)
                    }

                    habitName = ""
                }
            }
        ) {
            Text("習慣追加")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(habits) { habit ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(
                                    "detail/${habit.id}"
                                )
                            }
                    ) {

                        Text(habit.name)

                        Text(
                            "Lv.${calculateLevel(habit.xp)}"
                        )

                        Text(
                            "XP ${habit.xp}"
                        )
                    }

                    Button(
                        onClick = {

                            scope.launch {

                                withContext(Dispatchers.IO) {

                                    habitDao.deleteRecordsByHabit(
                                        habit.id
                                    )

                                    habitDao.deleteHabit(habit)
                                }

                                habits.remove(habit)
                            }
                        }
                    ) {
                        Text("削除")
                    }
                }
            }
        }
    }
}
fun calculateTitle(totalXp: Int): String {

    return when {
        totalXp >= 3000 -> "------伝説の習慣王------"
        totalXp >= 1500 -> "------習慣王------"
        totalXp >= 700 -> "------継続マスター------"
        totalXp >= 300 -> "------習慣戦士------"
        totalXp >= 100 -> "------見習い冒険者------"
        else -> "------ 習慣初心者------"
    }
}


fun calculateLevel(xp: Int): Int {

    return when {
        xp >= 500 -> 5
        xp >= 300 -> 4
        xp >= 150 -> 3
        xp >= 50 -> 2
        else -> 1
    }
}