package com.samuel.oremoschanganapt.presentation.uiStates

data class CommonPageUiState(
    val expanded: Boolean = false,
    val pageContentId: Int = 0,
    val pageNumber: Int = 0,
    val pageTitle: String = "",
    val pageSubTitle: String = "",
    val pageBody: String = "",
    val lovedIdPrays: Set<Int> = emptySet(),
    val lovedIdSongs: Set<Int> = emptySet()
)
