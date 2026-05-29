package com.samuel.oremoschanganapt.presentation.Songs

data class SongsUiState(
    val searchValue: String = "",
    val advancedSearchString: String = "",
    val activeInput: Int = 0,
    val searchInputActive: Boolean = false,
    val lovedSongsIds: Set<Int> = emptySet()
)