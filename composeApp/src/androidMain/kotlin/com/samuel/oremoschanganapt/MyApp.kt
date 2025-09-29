package com.samuel.oremoschanganapt

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

class MyApp: Application() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        AndroidSettingsHelper.initialize(this)
        val notificationChannel = NotificationChannel(
            "oremos_reminder",
            "oremos reminder",
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationChannel.description = "A notificaiton service"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}