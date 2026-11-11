package com.example.safewalk.utils

import android.content.Context

// This class remembers if the user is logged in.
// For now it uses SharedPreferences, which is a very simple way to save one value.
// The real Firebase login will replace this in Sprint 4.
class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("safewalk_session", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun saveDemoLogin() {
        prefs.edit().putBoolean("is_logged_in", true).apply()
    }
}
