package com.samuelsumbane.oremoscatolico.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun scheduleTaskAt(timestamp: Long, action: () -> Unit) {
    val delayMillis = timestamp - System.currentTimeMillis()
    if (delayMillis > 0) {
        GlobalScope.launch {
            delay(delayMillis)
            action()
        }
    } else {
        println("⏰ Time has passed")
    }
}