package com.svyatocheck.remainder.core

import android.content.Context
import android.content.SharedPreferences

class SharedPrefSettings constructor(
    private val appContext: Context
) {
    private val sharedPrefSettingsFilKey = "SHARED_PREF_SETTINGS"

    companion object {
        const val SHARED_PREF_USER_ID = "SHARED_PREF_USER_ID"
        const val SHARED_PREF_USER_TOKEN = "SHARED_PREF_USER_TOKEN"

    }

    private fun getSharedPref(): SharedPreferences {
        return appContext.getSharedPreferences(sharedPrefSettingsFilKey, Context.MODE_PRIVATE)
    }

    fun getToken(): String {
        return getSharedPref().getString(SHARED_PREF_USER_TOKEN, "")!!
    }

    fun putToken(token : String) {
        getSharedPref().edit().putString(SHARED_PREF_USER_TOKEN, token).apply()
    }

    fun getID(): String {
        return getSharedPref().getString(SHARED_PREF_USER_ID, "")!!
    }

    fun putID(value : String) {
        getSharedPref().edit().putString(SHARED_PREF_USER_ID, value).apply()
    }

}