package com.example.safewalk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.safewalk.screens.ActiveWalkScreen
import com.example.safewalk.screens.ContactsScreen
import com.example.safewalk.screens.HistorySettingsScreen
import com.example.safewalk.screens.HomeScreen
import com.example.safewalk.screens.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // the app always starts on the splash screen
    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onFinished = {
                    // popUpTo removes the splash from the back stack
                    // so the back button does not return to it
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                onStartWalk = { navController.navigate(Routes.ACTIVE_WALK) },
                onOpenContacts = { navController.navigate(Routes.CONTACTS) },
                onOpenSettings = { navController.navigate(Routes.HISTORY_SETTINGS) }
            )
        }

        composable(Routes.ACTIVE_WALK) {
            ActiveWalkScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.CONTACTS) {
            ContactsScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.HISTORY_SETTINGS) {
            HistorySettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}