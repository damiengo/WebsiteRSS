package com.damiengo.websiterss.util

import android.content.Context
import android.content.SharedPreferences
import com.damiengo.websiterss.R
import javax.inject.Inject

class ThemeUtil @Inject constructor(private val context: Context) {

    private val darkKey = "isDark"
    private val prefs: SharedPreferences = context.getSharedPreferences("preferences", 0)

    fun switchTheme() {
        changePref()
    }

    fun applyTheme() {
        if(this.isDark()) {
            context.theme.applyStyle(R.style.ThemeDark, true)
        }
        else {
            context.theme.applyStyle(R.style.AppTheme, true)
        }
    }

    private fun changePref() {
        var newIsDark = true
        if(this.isDark()) {
            newIsDark = false
        }
        this.prefs.edit().putBoolean(this.darkKey, newIsDark).apply()
    }

    fun isDark() : Boolean {
        return this.prefs.getBoolean(this.darkKey, false)
    }

}