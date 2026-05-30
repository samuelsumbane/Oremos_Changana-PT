package com.samuel.oremoschanganapt.ui_core.globalComponents

import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.HomeScreen
import com.samuel.oremoschanganapt.ui_core.AgroupedSongsScreen
import com.samuel.oremoschanganapt.ui_core.CommonSettingsScreen
import com.samuel.oremoschanganapt.ui_core.MorePagesScreen
import com.samuel.oremoschanganapt.presentation.commonPrays.PraysScreen
import com.samuel.oremoschanganapt.ui_core.PageName

fun appRouter(navigator: Navigator, page: String) {
    navigator.push(
        when (page) {
            PageName.HOME.value -> HomeScreen()
            PageName.PRAYS.value -> PraysScreen
            PageName.SONGSGROUP.value -> AgroupedSongsScreen
            PageName.SETTINGS.value -> CommonSettingsScreen
            else -> MorePagesScreen
        }
    )
}