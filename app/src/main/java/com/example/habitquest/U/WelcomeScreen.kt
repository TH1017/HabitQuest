package com.example.habitquest.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.habitquest.R

@Composable
fun WelcomeScreen(
    navController: NavController
) {

    var startAnimation by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1500
        ),
        label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.habitquest_logo
            ),
            contentDescription = "HabitQuest Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .alpha(alphaAnimation),
            contentScale = ContentScale.Fit
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "習慣をクエストに変えよう"
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = {
                navController.navigate("home") {
                    popUpTo("welcome") {
                        inclusive = true
                    }
                }
            }
        ) {
            Text("⚔️ 冒険を始める")
        }
    }
}