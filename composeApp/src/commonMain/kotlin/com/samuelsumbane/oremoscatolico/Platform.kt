package com.samuelsumbane.oremoscatolico

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


fun isAndroid(): Boolean {
    return try {
        Class.forName("android.app.Activity")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

fun isDesktop(): Boolean = !isAndroid()