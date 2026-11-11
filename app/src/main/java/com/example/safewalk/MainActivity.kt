package com.example.safewalk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.safewalk.navigation.AppNavigation
import com.example.safewalk.ui.theme.SafeWalkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SafeWalkTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // safeDrawingPadding keeps the content away from the status bar and keyboard
                    Box(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}