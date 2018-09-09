package com.techonlabs.androidboilerplate.utils

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
    PreferenceManager.getDefaultSharedPreferences(this).edit(commit, action)
}

fun Fragment.editPrefs(
        commit: Boolean = false,
        action: SharedPreferences.Editor.() -> Unit) {
    prefs.edit(commit, action)
}

inline val Fragment.prefs: SharedPreferences
    get() {
        return PreferenceManager.getDefaultSharedPreferences(context
                ?: throw Exception("Context not found while storing shared preference"))
    }


object Prefs {
    const val KEY = "KEY"
}

fun Fragment.nukePrefs() {
    val editor = prefs.edit()
    editor.clear()
    editor.apply()
}