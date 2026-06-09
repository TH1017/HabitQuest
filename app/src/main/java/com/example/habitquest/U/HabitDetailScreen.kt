package com.example.habitquest.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HabitDetailScreen(
    habitName: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = habitName
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

        Text(
            text = "達成履歴はここに表示"
        )
    }
}