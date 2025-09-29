package com.samuel.oremoschanganapt

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.samuel.oremoschanganapt.R
import kotlin.random.Random

class AppNotificationService(
    private val context: Context,
    val title: String,
    val text: String,
    val contentTable: String,
    val contentId: Int,
) {
    @RequiresApi(Build.VERSION_CODES.M)
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    @RequiresApi(Build.VERSION_CODES.M)
    fun showBasicNotification() {
        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("FROM_NOTIFICATION", true)
            putExtra("CONTENT_TABLE", contentTable)
            putExtra("CONTENT_ID", contentId)
        }

        val pendingActivityIntent = PendingIntent.getActivity(
            context,
            0,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "oremos_reminder")
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.oremospic)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingActivityIntent)

            .setNumber(0)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
}