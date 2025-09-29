package com.samuel.oremoschanganapt


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
