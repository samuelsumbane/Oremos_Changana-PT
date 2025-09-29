package com.samuel.oremoschanganapt

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ReminderReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("NOTIFICATION_TITLE")
        val message = intent.getStringExtra("NOTIFICATION_MESSAGE")
        val contentTable = intent.getStringExtra("CONTENT_TABLE")
//        val contentId = intent.getStringExtra("CONTENT_ID")?.toInt()
        val contentId = intent.getIntExtra("CONTENT_ID", 0)


        val appNotificationService = AppNotificationService(context, title!!, message!!, contentTable !!, contentId)
        appNotificationService.showBasicNotification()
    }
}
