package com.example.habitquest.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habitquest.database.HabitDao

@Composable
fun AppNavigation(habitDao: HabitDao) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {

            HomeScreen(
                habitDao = habitDao,
                navController = navController
            )
        }

        composable("detail/{habitId}") { backStackEntry ->

            val habitId =
                backStackEntry.arguments
                    ?.getString("habitId")
                    ?.toIntOrNull() ?: 0

            HabitDetailScreen(
                habitId = habitId,
                habitDao = habitDao
            )
        }
    }
}