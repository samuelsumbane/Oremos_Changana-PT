package com.samuel.oremoschanganapt

import org.koin.core.context.GlobalContext.startKoin

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}