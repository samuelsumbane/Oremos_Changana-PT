package com.samuelsumbane.oremoscatolico


data class Reminder(
    val id: Long = 0,
    val reminderData: Int,
    val reminderTable: String,
    val reminderDateTime: Long
)
