package com.example.safewalk.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safewalk.utils.hasLocationPermission

@Composable
fun HomeScreen(
    onStartWalk: () -> Unit,
    onOpenContacts: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val context = LocalContext.current

    // true when the phone is turned sideways
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    // rememberSaveable keeps the values when the phone is rotated
    var destination by rememberSaveable { mutableStateOf("") }
    var etaMinutes by rememberSaveable { mutableStateOf(30) }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    // this runs when the user presses Start Walk
    fun checkAndStart() {
        val cleanDestination = destination.trim()

        if (cleanDestination.length < 3) {
            errorMessage = "Please type a destination with at least 3 letters."
        } else if (cleanDestination.length > 100) {
            errorMessage = "That destination is too long. Please use 100 letters or less."
        } else {
            errorMessage = ""
            onStartWalk()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(text = "SafeWalk", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text(text = "Where are you walking to tonight?")

        Spacer(modifier = Modifier.height(16.dp))

        // in landscape the form and recent walks sit side by side
        if (isLandscape) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    WalkForm(
                        destination = destination,
                        onDestinationChange = { destination = it },
                        etaMinutes = etaMinutes,
                        onEtaChange = { etaMinutes = it },
                        errorMessage = errorMessage,
                        onStartClick = { checkAndStart() }
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    RecentWalksCard()
                }
            }
        } else {
            WalkForm(
                destination = destination,
                onDestinationChange = { destination = it },
                etaMinutes = etaMinutes,
                onEtaChange = { etaMinutes = it },
                errorMessage = errorMessage,
                onStartClick = { checkAndStart() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            RecentWalksCard()
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(onClick = onOpenContacts, modifier = Modifier.weight(1f)) {
                Text("Contacts")
            }
            OutlinedButton(onClick = onOpenSettings, modifier = Modifier.weight(1f)) {
                Text("History and Settings")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // only checks the permission, the app does not ask for it yet
        val permissionText = if (hasLocationPermission(context)) {
            "Location permission is given."
        } else {
            "Location permission is not given yet. The app will ask for it in Sprint 3."
        }
        Text(text = permissionText, fontSize = 12.sp)
    }
}

@Composable
fun WalkForm(
    destination: String,
    onDestinationChange: (String) -> Unit,
    etaMinutes: Int,
    onEtaChange: (Int) -> Unit,
    errorMessage: String,
    onStartClick: () -> Unit
) {
    // the time choices the user can pick from
    val etaOptions = listOf(15, 30, 45, 60)

    Column {
        OutlinedTextField(
            value = destination,
            onValueChange = onDestinationChange,
            label = { Text("Destination") },
            singleLine = true,
            isError = errorMessage.isNotEmpty(),
            supportingText = {
                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage)
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "How long will your walk take?")

        Spacer(modifier = Modifier.height(8.dp))

        // the chosen time is a filled button, the others are only outlined
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (minutes in etaOptions) {
                if (minutes == etaMinutes) {
                    Button(
                        onClick = { onEtaChange(minutes) },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("$minutes min")
                    }
                } else {
                    OutlinedButton(
                        onClick = { onEtaChange(minutes) },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("$minutes min")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onStartClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Text(text = "Start Walk", fontSize = 20.sp)
        }
    }
}

@Composable
fun RecentWalksCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Recent walks", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "No walks yet. Your last walks will show here after Sprint 3.")
        }
    }
}