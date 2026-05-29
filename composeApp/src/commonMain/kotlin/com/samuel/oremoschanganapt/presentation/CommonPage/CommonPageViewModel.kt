package com.samuel.oremoschanganapt.presentation.CommonPage

import androidx.lifecycle.ViewModel
import com.samuel.oremoschanganapt.presentation.ConfigEntry
import com.samuel.oremoschanganapt.presentation.ConfigScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CommonPageViewModel() : ViewModel() {

    private val _state = MutableStateFlow(CommonPageUiState())
    val commonPageUiState = _state.asStateFlow()

    fun updateState(block: (CommonPageUiState) -> CommonPageUiState) {
        _state.update(block)
    }

    fun fillCommonPageForm(
        expanded: Boolean? = null,
        pageContentId: Int? = null,
        pageNumber: Int? = null,
        pageTitle: String? = null,
        pageSubTitle: String? = null,
        pageBody: String? = null,
        lovedIdPrays: (Set<Int>)? = null,
        lovedIdSongs: (Set<Int>)? = null
    ) {
        expanded?.let { nValue -> updateState { it.copy(expanded = nValue) } }
        pageContentId?.let { nValue -> updateState { it.copy(pageContentId = nValue) } }
        pageNumber?.let { nValue -> updateState { it.copy(pageNumber = nValue) } }
        pageTitle?.let { nValue -> updateState { it.copy(pageTitle = nValue) } }
        pageSubTitle?.let { nValue -> updateState { it.copy(pageSubTitle = nValue) } }
        pageBody?.let { nValue -> updateState { it.copy(pageBody = nValue) } }
        lovedIdPrays?.let { nValue -> updateState { it.copy(lovedIdPrays = nValue) } }
        lovedIdSongs?.let { nValue -> updateState { it.copy(lovedIdSongs = nValue) } }
    }

    fun modifyLovedIdSongs(songId: Int, configViewModel: ConfigScreenViewModel) {
       updateState { it.copy(lovedIdSongs =
           if (songId in commonPageUiState.value.lovedIdSongs) it.lovedIdSongs - songId else it.lovedIdSongs + songId
       ) }

       configViewModel.saveConfiguration(ConfigEntry.FavoriteSongs, commonPageUiState.value.lovedIdSongs)
    }

    fun modifyLovedIdPrays(prayId: Int, configViewModel: ConfigScreenViewModel) {
        updateState { it.copy(lovedIdPrays =
            if (prayId in commonPageUiState.value.lovedIdPrays) it.lovedIdPrays - prayId else it.lovedIdPrays + prayId
        ) }
        configViewModel.saveConfiguration(ConfigEntry.FavoritePrays, commonPageUiState.value.lovedIdPrays)
    }


}