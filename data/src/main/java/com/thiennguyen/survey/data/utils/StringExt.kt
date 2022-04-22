package com.thiennguyen.survey.data.utils

import java.util.Date

fun String?.isValidAccessToken(expiredIn: Long): Boolean {
    if (this.isNullOrEmpty()) return false
    return try {
        val today = Date(System.currentTimeMillis())
        return today.after(Date(expiredIn))
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}