package com.samuelsumbane.oremoscatolico.globalComponents

import cafe.adriel.voyager.navigator.Navigator
import com.samuelsumbane.oremoscatolico.HomeScreen
import com.samuelsumbane.oremoscatolico.commonView.AgroupedSongsScreen
import com.samuelsumbane.oremoscatolico.commonView.CommonSettingsScreen
import com.samuelsumbane.oremoscatolico.commonView.MorePagesScreen
import com.samuelsumbane.oremoscatolico.commonView.PraysScreen
import com.samuelsumbane.oremoscatolico.repository.PageName

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