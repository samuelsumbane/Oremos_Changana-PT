package com.samuel.oremoschanganapt.presentation.CommonPrays

import com.samuel.oremoschanganapt.presentation.ConfigScreenViewModel
import com.samuel.oremoschanganapt.ui_core.globalComponents.Pray

sealed interface CommonPraysUiEvents {
    data class OnHeartClicked(
        val prayId: Int,
        val configViewModel: ConfigScreenViewModel
    ) : CommonPraysUiEvents
    data class OnSetPraysList(val prays: List<Pray>) : CommonPraysUiEvents
    data class OnSearchPray(val prayName: String) : CommonPraysUiEvents
}