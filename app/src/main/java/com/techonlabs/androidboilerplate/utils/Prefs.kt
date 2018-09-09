package com.techonlabs.androidboilerplate.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment

fun SharedPreferences.getBoolean(key: String, defValue: Boolean = false) = this.getBoolean(key, defValue)
fun SharedPreferences.getString(key: String, defValue: String = ""): String = this.getString(key, defValue)!!

fun AppCompatActivity.editPrefs(
        commit: Boolean = false,
        action: SharedPreferences.Editor.() -> Unit) {
    defaultSharedPreferences.edit(commit, action)
}

fun Fragment.editPrefs(
        commit: Boolean = false,
        action: SharedPreferences.Editor.() -> Unit) {
    prefs.edit(commit, action)
}

inline val Fragment.prefs: SharedPreferences
    get() = context?.defaultSharedPreferences
            ?: throw Exception("Context not found while storing shared preference")


inline val Context.defaultSharedPreferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(this)


object Prefs {
    const val KEY = "KEY"
}

fun Fragment.nukePrefs() {
    val editor = prefs.edit()
    editor.clear()
    editor.apply()
}