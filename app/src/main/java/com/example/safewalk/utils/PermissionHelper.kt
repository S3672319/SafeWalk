package com.example.safewalk.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

// This only checks the permission. It does not ask for it.
// Asking for location will be added in Sprint 3.
fun hasLocationPermission(context: Context): Boolean {
    val result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
    return result == PackageManager.PERMISSION_GRANTED
}