package com.thiennguyen.survey.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceManager @Inject constructor(private val context: Context) {

    companion object {
        private const val PREF_NAME = "PREF_SURVEY"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val CREATED_AT = "CREATED_AT"
        private const val EXPIRED_IN = "EXPIRED_IN"
        private const val TOKEN_TYPE = "TOKEN_TYPE"
        private const val IS_LOGGED_IN = "IS_LOGGED_IN"
    }

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setTokenData(
        accessToken: String? = null,
        refreshToken: String? = null,
        tokenType: String? = null,
        createdAt: Long? = null,
        expiredIn: Long? = null
    ) {
        accessToken?.let { setString(ACCESS_TOKEN, it) }
        refreshToken?.let { setString(REFRESH_TOKEN, it) }
        tokenType?.let { setString(TOKEN_TYPE, it) }
        createdAt?.let { setLong(CREATED_AT, it) }
        expiredIn?.let { setLong(EXPIRED_IN, it) }
        setBool(IS_LOGGED_IN, true)
    }

    fun getTokenType(): String? {
        return getString(TOKEN_TYPE)
    }

    fun getAccessToken(): String? {
        return getString(ACCESS_TOKEN)
    }

    fun getRefreshToken(): String? {
        return getString(REFRESH_TOKEN)
    }

    fun getExpiredIn(): Long {
        return getLong(EXPIRED_IN)
    }

    private fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    private fun setString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    private fun getLong(key: String): Long {
        return preferences.getLong(key, 0L)
    }

    private fun setLong(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
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
