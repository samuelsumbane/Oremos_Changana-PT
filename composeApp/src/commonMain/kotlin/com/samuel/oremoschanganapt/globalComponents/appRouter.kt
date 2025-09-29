package com.samuel.oremoschanganapt.globalComponents

import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.HomeScreen
import com.samuel.oremoschanganapt.commonView.AgroupedSongsScreen
import com.samuel.oremoschanganapt.commonView.CommonSettingsScreen
import com.samuel.oremoschanganapt.commonView.MorePagesScreen
import com.samuel.oremoschanganapt.commonView.PraysScreen
import com.samuel.oremoschanganapt.repository.PageName

fun appRouter(navigator: Navigator, page: String) {
    navigator.push(
        when (page) {
            PageName.HOME.value -> HomeScreen
            PageName.PRAYS.value -> PraysScreen
            PageName.SONGSGROUP.value -> AgroupedSongsScreen
            PageName.SETTINGS.value -> CommonSettingsScreen
            else -> MorePagesScreen
        }
    )
}