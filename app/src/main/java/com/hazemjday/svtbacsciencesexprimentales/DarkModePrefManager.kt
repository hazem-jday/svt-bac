package com.hazemjday.svtbacsciencesexprimentales

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by kapil on 20/01/17.
 */
class DarkModePrefManager(var _context: Context) {
    private var pref: SharedPreferences
    var editor: SharedPreferences.Editor

    // shared pref mode
    private var privateMode = 0

    init {
        pref = _context.getSharedPreferences(PREF_NAME, privateMode)
        editor = pref.edit()
    }

    fun setDarkMode(isFirstTime: Boolean) {
        editor.putBoolean(IS_NIGHT_MODE, isFirstTime)
        editor.commit()
    }

    val isNightMode: Boolean
        get() = pref.getBoolean(IS_NIGHT_MODE, false)

    companion object {
        // Shared preferences file name
        private const val PREF_NAME = "dark-mode"
        private const val IS_NIGHT_MODE = "IsNightMode"
    }
}