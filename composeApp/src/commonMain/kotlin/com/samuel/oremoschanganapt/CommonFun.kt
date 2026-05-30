package com.samuel.oremoschanganapt

import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinAppDeclaration

//fun initKoin() {
//    startKoin {
//        modules(appModule)
//    }
//}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule)
    }
}