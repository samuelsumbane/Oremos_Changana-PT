package com.samuelsumbane.oremoscatolico.repository

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime


enum class DataCollection { SONGS, PRAYS }


// Verify if is number or not --------->>
fun isNumber(valor: Any): Boolean {
    return try {
        // Try to convert value to numeric type
        when (valor) {
            is Byte, is Short, is Int, is Long, is Float, is Double -> true
            is String -> {
                // Try to convert string to number ---------->>
                valor.toDouble()
                true
            }
            else -> false
        }
    } catch (e: NumberFormatException) {
        false
    }
}



fun convertLongToDateString(long: Long): String {
    val instant = Instant.fromEpochMilliseconds(long)
    val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
    return "%02d/%02d/%04d".format(localDate.dayOfMonth, localDate.monthNumber, localDate.year)
}

fun convertTimePickerStateToLong(hour: Int, minute: Int): Long {
    val localTime = LocalTime(hour, minute)
    return localTime.toSecondOfDay() * 1000L
}

fun convertLongToTimeString(timeInMillis: Long): String {
    val instant = Instant.fromEpochMilliseconds(timeInMillis)
    val time = instant.toLocalDateTime(TimeZone.UTC).time
    return "%02d:%02d".format(time.hour, time.minute)
}

//fun dateStringToLong(dateInString: String): Long {
//    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
//    val target = when (dateInString) {
//        "Today" -> now
//        "Tomorrow" -> now.plus(DatePeriod(days = 1))
//        "Next week" -> now.plus(DatePeriod(weeks = 1))
//        "Next Month" -> now.plus(DatePeriod(months = 1))
//        else -> throw IllegalArgumentException("Invalid date string")
//    }
//    return target.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
//}

fun timeStringToLong(timeOfDay: String): Long {
    val localTime = when (timeOfDay) {
        "Morning (9:00)" -> LocalTime(9, 0)
        "Noon (12:00)" -> LocalTime(12, 0)
        "Afternoon (15:00)" -> LocalTime(15, 0)
        "Evening (18:00)" -> LocalTime(18, 0)
        "Late evening (21:00)" -> LocalTime(21, 0)
        else -> throw IllegalArgumentException("Invalid time of day")
    }
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val datetime = LocalDateTime(today, localTime)
    return datetime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun combineTimestamps(dateMillis: Long, timeMillis: Long): Long {
    val date = Instant.fromEpochMilliseconds(dateMillis)
        .toLocalDateTime(TimeZone.of("Africa/Maputo"))
        .date

    val hora = LocalTime.fromSecondOfDay((timeMillis / 1000).toInt())
    val dataHora = LocalDateTime(date, hora)

    return dataHora.toInstant(TimeZone.of("Africa/Maputo")).toEpochMilliseconds()
}


fun splitTimestamp(timestamp: Long): Pair<Long, Long> {
    val zone = TimeZone.of("Africa/Maputo")
    val dateTime = Instant.fromEpochMilliseconds(timestamp).toLocalDateTime(zone)

    // Data em milissegundos (meia-noite do mesmo dia)
    val dateMillis = LocalDateTime(dateTime.date, LocalTime(0, 0))
        .toInstant(zone)
        .toEpochMilliseconds()

    // Hora em milissegundos (desde meia-noite)
    val timeMillis = dateTime.time.toSecondOfDay() * 1000L

    return dateMillis to timeMillis
}

fun getCurrentTimestamp(): Long = System.currentTimeMillis()

fun parseStyledText(text: String): AnnotatedString {
    return buildAnnotatedString {
        var i = 0
        var bold = false
        var italic = false

        while (i < text.length) {
            when {
                text.startsWith("\\b", i) -> {
                    bold = !bold
                    i += 2
                }
                text.startsWith("\\i", i) -> {
                    italic = !italic
                    i += 2
                }
                else -> {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
                            fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal
                        )
                    ) {
                        append(text[i])
                    }
                    i++
                }
            }
        }
    }
}

