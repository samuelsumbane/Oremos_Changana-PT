package com.samuel.oremoschanganapt.presentation.lovedData

import com.samuel.oremoschanganapt.domain.DataCollection
import com.samuel.oremoschanganapt.presentation.ConfigScreenViewModel

sealed interface LovedDataUiEvents {
    data class OnHeartClicked(
        val dataCollection: DataCollection,
        val itemId: Int,
        val configScreenViewModel: ConfigScreenViewModel
    ) : LovedDataUiEvents
    data class OnSearchPrayOrSong(val searchValue: String) : LovedDataUiEvents
    data class OnExpandSearchInput(val expanded: Boolean) : LovedDataUiEvents
}