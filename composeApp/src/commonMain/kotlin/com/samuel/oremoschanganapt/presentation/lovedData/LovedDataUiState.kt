package com.samuel.oremoschanganapt.presentation.lovedData

import com.samuel.oremoschanganapt.ui_core.globalComponents.Pray
import com.samuel.oremoschanganapt.ui_core.globalComponents.Song

data class LovedDataUiState(
    val searchValue: String = "",
    val lovedPrays: List<Pray> = emptyList(),
    val lovedSongs: List<Song> = emptyList(),
    val lovedIdPrays: Set<Int> = emptySet(),
    val lovedIdSongs: Set<Int> = emptySet(),
    val searchInputExpanded: Boolean = false
)
