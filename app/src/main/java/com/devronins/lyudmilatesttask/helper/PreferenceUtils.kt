package com.devronins.lyudmilatesttask.helper

import android.content.Context
import android.content.SharedPreferences
import com.devronins.lyudmilatesttask.MyApplication

object PreferenceUtils {

    const val KEY_FAVORITE_GYM_ITEMS = "favoriteGymItems"
    const val KEY_SELECTED_TYPES = "selectedTypes"
    const val KEY_FAVORITE_CLASS = "favoriteClass"

    private fun getSharedPreference(): SharedPreferences? {
        return MyApplication.appContext?.run {
            getSharedPreferences("$packageName.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        } ?: run {
            null
        }
    }

    fun saveString(key: String, value: String) {
        val sharedPref = getSharedPreference()
        sharedPref?.run {
            with (sharedPref.edit()) {
                putString(key, value)
                apply()
            }
        }
    }

    fun getString(key: String): String? {
        val sharedPref = getSharedPreference()
        sharedPref?.run {
             return sharedPref.getString(key, null)
        } ?: run {
            return null
        }
    }

}