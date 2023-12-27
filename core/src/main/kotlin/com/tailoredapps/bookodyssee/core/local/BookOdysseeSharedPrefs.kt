package com.tailoredapps.bookodyssee.core.local

import android.content.Context

interface SharedPrefs {
    var userId: Int
}

class BookOdysseeSharedPrefs(
    context: Context
) : SharedPrefs {
    companion object {
        private const val USER_ID = "user_id"
    }

    private val prefs = context.getSharedPreferences("shared_prefs", 0)

    override var userId: Int
        get() = prefs.getInt(USER_ID, 0)
        set(value) {
            prefs.edit().putInt(USER_ID, value).apply()
        }
}
