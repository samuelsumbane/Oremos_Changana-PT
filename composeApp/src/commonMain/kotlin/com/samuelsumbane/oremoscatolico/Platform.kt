package com.samuelsumbane.oremoscatolico


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
