package com.samuelsumbane.oremoscatolico

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class ReminderReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("NOTIFICATION_TITLE")
        val message = intent.getStringExtra("NOTIFICATION_MESSAGE")

        val appNotificationService = AppNotificationService(context, title!!, message!!)
        appNotificationService.showBasicNotification()
    }
}
