package com.thiennguyen.survey.service

import android.content.Context
import android.content.Intent
import com.thiennguyen.survey.data.interceptor.LogoutService
import javax.inject.Inject

class LogoutServiceImpl @Inject constructor(private val context: Context) : LogoutService {

    override fun startService() {
        context.startService(Intent(context, MainLogoutService::class.java))
    }
}