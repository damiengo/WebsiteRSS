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
        applyTheme()
    }

    fun applyTheme() {
        if(this.isDark()) {
            context.setTheme(R.style.ThemeDark)
        }
        else {
            context.setTheme(R.style.AppTheme)
        }
    }

    private fun changePref() {
        var newIsDark = true
        if(this.isDark()) {
            newIsDark = false
        }
        this.prefs.edit().putBoolean(this.darkKey, newIsDark)
        this.prefs.edit().commit()
    }

    private fun isDark() : Boolean {
        return this.prefs.getBoolean(this.darkKey, false)
    }

}