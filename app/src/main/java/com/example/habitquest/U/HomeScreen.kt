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

@Composable
fun HomeScreen(
    habitDao: HabitDao,
    navController: NavController
) {

    var habitName by remember {
        mutableStateOf("")
    }

    val habits = remember {
        mutableStateListOf<String>()
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

        val savedHabits = withContext(Dispatchers.IO) {
            habitDao.getAllHabits()
        }

        habits.clear()

        habits.addAll(
            savedHabits.map { it.name }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("HabitQuest")

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

                    val newHabit = habitName

                    habits.add(newHabit)

                    scope.launch {
                        habitDao.insertHabit(
                            com.example.habitquest.data.Habit(
                                name = newHabit
                            )
                        )
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

                Text(
                    text = habit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate(
                                "detail/$habit"
                            )
                        }
                )
            }
        }
    }
}