package com.samuelsumbane.oremoscatolico

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.samuelsumbane.oremoscatolico.R
import kotlin.random.Random

class AppNotificationService(
    private val context: Context,
    val title: String,
    val text: String,
) {
    @RequiresApi(Build.VERSION_CODES.M)
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    @RequiresApi(Build.VERSION_CODES.M)
    fun showBasicNotification() {
        val notification = NotificationCompat.Builder(context, "oremos_reminder")
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.oremospic)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .setNumber(0)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
}