package com.samuel.oremoschanganapt.presentation.viewModels

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.core.screen.Screen
import com.samuel.oremoschanganapt.presentation.uiStates.SongsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SongsViewModel : ViewModel() {
    val _songsState = MutableStateFlow(SongsUiState())
    val songsUiState = _songsState.asStateFlow()


    fun updateState(block: (SongsUiState) -> SongsUiState) {
        _songsState.update(block)
    }

    fun fillSongsForm(
        searchValue: String? = null,
        advancedSearchString: String? = null,
        activeInput: Int? = null,
        searchInputActive: Boolean? = null,
        lovedSongsIds: (Set<Int>)? = null
    ) {
        searchValue?.let { nValue -> updateState { it.copy(searchValue = nValue) } }
        advancedSearchString?.let { nValue -> updateState { it.copy(advancedSearchString = nValue) } }
        activeInput?.let { nValue -> updateState { it.copy(activeInput = nValue) } }
        searchInputActive?.let { nValue -> updateState { it.copy(searchInputActive = nValue) } }
        lovedSongsIds?.let { nValue -> updateState { it.copy(lovedSongsIds = nValue) } }
    }
}