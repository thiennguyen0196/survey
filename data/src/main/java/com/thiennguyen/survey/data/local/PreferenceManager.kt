package com.thiennguyen.survey.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceManager @Inject constructor(private val context: Context) {

    companion object {
        private const val PREF_NAME = "PREF_SURVEY"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val IS_LOGGED_IN = "IS_LOGGED_IN"
    }

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setUserLoggedInData(accessToken: String?, refreshToken: String?) {
        setString(ACCESS_TOKEN, accessToken.orEmpty())
        setString(REFRESH_TOKEN, refreshToken.orEmpty())
        setBool(IS_LOGGED_IN, true)
    }

    private fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    private fun setString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    private fun getBool(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    private fun setBool(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun clear() {
        preferences.edit().clear().apply()
    }
}
