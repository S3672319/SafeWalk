package com.example.safewalk.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safewalk.utils.SessionManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val context = LocalContext.current

    // this changes to true when the screen opens, and that starts the animation
    var started by remember { mutableStateOf(false) }

    // the logo grows from small to full size
    val logoScale by animateFloatAsState(
        targetValue = if (started) 1f else 0.4f,
        animationSpec = tween(durationMillis = 1000),
        label = "logoScale"
    )

    // the logo also fades in
    val logoAlpha by animateFloatAsState(
        targetValue = if (started) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "logoAlpha"
    )

    LaunchedEffect(Unit) {
        started = true
        delay(2000) // show the logo for 2 seconds

        // check the login status
        val session = SessionManager(context)
        if (!session.isLoggedIn()) {
            // there is no login screen yet, because Firebase login comes in Sprint 4
            // so for now I save a demo login
            session.saveDemoLogin()
        }

        onFinished()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(logoScale)
                .alpha(logoAlpha)
        ) {
            // a simple round logo made with a Box and two letters
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SW",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "SafeWalk", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text(text = "Someone is watching over your walk home", fontSize = 14.sp)
        }
    }
}