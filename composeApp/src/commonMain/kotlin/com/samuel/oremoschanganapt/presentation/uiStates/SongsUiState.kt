package com.samuel.oremoschanganapt.presentation.uiStates

data class SongsUiState(
    val searchValue: String = "",
    val advancedSearchString: String = "",
    val activeInput: Int = 0,
    val searchInputActive: Boolean = false,
    val lovedSongsIds: Set<Int> = emptySet()
)
