package com.thiennguyen.survey.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.thiennguyen.survey.feature.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainLogoutService : Service() {

    private var isRunning = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (isRunning) {
            return super.onStartCommand(intent, flags, startId)
        }
        isRunning = true

        val mainIntent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(mainIntent)
        stopSelf()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}
