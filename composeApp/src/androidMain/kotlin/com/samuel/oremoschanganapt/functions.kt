package com.samuel.oremoschanganapt
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.or
import kotlin.text.compareTo


@SuppressLint("ScheduleExactAlarm")
@RequiresApi(Build.VERSION_CODES.M)
fun scheduleNotificationForSongOrPray(
    context: Context,
    title: String,
    message: String,
    timestamp: Long,
    contentTable: String,
    contentId: Int
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // Check permission (from Android 12)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            context.startActivity(intent)
            return
        }
    }

    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra("NOTIFICATION_TITLE", title)
        putExtra("NOTIFICATION_MESSAGE", message)
        putExtra("CONTENT_TABLE", contentTable)
        putExtra("CONTENT_ID", contentId)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        timestamp,
        pendingIntent
    )
}



fun updateLocale(context: Context, locale: Locale) {
    val resources = context.resources
    val configuration = resources.configuration
    configuration.setLocale(locale)
    val displayMetrics = resources.displayMetrics
    resources.updateConfiguration(configuration, displayMetrics)

    // Para versões posteriores ao Android 7.0 (API 24)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.createConfigurationContext(configuration)
    }
}


fun restartActivity(context: Context) {
    val activity = context as? Activity
    activity?.recreate()
}


fun shareText(context: Context, text: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
    context.startActivity(Intent.createChooser(shareIntent, "Compartilhar via"))
}


@RequiresApi(Build.VERSION_CODES.O)
fun convertLongToDateString(long: Long): String{
    val instant = Instant.ofEpochMilli(long)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
fun convertTimePickerStateToLong(timePickerState: TimePickerState): Long {
    val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
    return selectedTime.toSecondOfDay() * 1000L
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertLongToTimeString(timeInMillis: Long): String {
    val time = Instant.ofEpochMilli(timeInMillis)
        .atOffset(ZoneOffset.UTC)
        .toLocalTime()
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return time.format(formatter)
}


@RequiresApi(Build.VERSION_CODES.O)
fun dateStringToLong(dateInString: String): Long{
    val now = LocalDateTime.now()
    return when (dateInString) {
        "Today" -> now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        "Tomorrow" -> now.plusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        "Next week" -> now.plusWeeks(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        "Next Month" -> now.plusMonths(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        else -> throw IllegalArgumentException("Invalid date string")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun timeStringToLong(timeOfDay: String): Long {
    val localTime = when (timeOfDay) {
        "Morning (9:00)" -> LocalTime.of(9, 0)
        "Noon (12:00)" -> LocalTime.of(12, 0)
        "Afternoon (15:00)" -> LocalTime.of(15, 0)
        "Evening (18:00)" -> LocalTime.of(18, 0)
        "Late evening (21:00)" -> LocalTime.of(21, 0)
        else -> throw IllegalArgumentException("Invalid time of day")
    }
    // Convert LocalTime to Long representing miliseconds
    return localTime.atDate(LocalDate.now())
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
fun combineTimestamps(dateMillis: Long, timeMillis: Long): Long {
    val data = Instant.ofEpochMilli(dateMillis).atZone(ZoneId.of("Africa/Maputo")).toLocalDate()
    val hora = LocalTime.ofSecondOfDay(timeMillis / 1000)
    val dataHora = LocalDateTime.of(data, hora)
    return dataHora.atZone(ZoneId.of("Africa/Maputo")).toInstant().toEpochMilli()
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun splitTimestamp(timestamp: Long): Pair<Long, Long> {
//    val zoneId = ZoneId.of("Africa/Maputo")
//    val dateTime = Instant.ofEpochMilli(timestamp).atZone(zoneId).toLocalDateTime()
//
//    // Extract date in milissegundos
//    val dateMillis = dateTime.toLocalDate()
//        .atStartOfDay(zoneId)
//        .toInstant()
//        .toEpochMilli()
//
//    // Extract time in milissegundos
//    val timeMillis = dateTime.toLocalTime().toSecondOfDay() * 1000L
//    return Pair(dateMillis, timeMillis)
//}


fun compareWithCurrentTime(targetMillis: Long): Boolean = System.currentTimeMillis() < targetMillis

