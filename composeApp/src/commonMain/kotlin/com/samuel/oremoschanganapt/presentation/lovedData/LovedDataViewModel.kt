package com.samuel.oremoschanganapt.presentation.lovedData

import androidx.lifecycle.ViewModel
import com.samuel.oremoschanganapt.domain.DataCollection
import com.samuel.oremoschanganapt.praysList
import com.samuel.oremoschanganapt.presentation.ConfigEntry
import com.samuel.oremoschanganapt.presentation.ConfigScreenViewModel
import com.samuel.oremoschanganapt.songsList
import com.samuel.oremoschanganapt.ui_core.globalComponents.Pray
import com.samuel.oremoschanganapt.ui_core.globalComponents.Song
import com.samuel.oremoschanganapt.ui_core.states.AppState.isLoading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LovedDataViewModel : ViewModel() {
    private val _state = MutableStateFlow(LovedDataUiState())
    val lovedDataUiState = _state.asStateFlow()

    fun updateState(block: (LovedDataUiState) -> LovedDataUiState) = _state.update(block)

    fun onEvent(lovedDataUiEvents: LovedDataUiEvents) {
        when (lovedDataUiEvents) {
            is LovedDataUiEvents.OnHeartClicked -> onHeartClicked(lovedDataUiEvents.dataCollection, lovedDataUiEvents.itemId, lovedDataUiEvents.configScreenViewModel)
            is LovedDataUiEvents.OnSearchPrayOrSong -> onSearchPrayOrSong(lovedDataUiEvents.searchValue)
            is LovedDataUiEvents.OnExpandSearchInput -> searchInputExpanded(lovedDataUiEvents.expanded)
        }
    }

    fun searchInputExpanded(expanded: Boolean) {
        updateState { it.copy(searchInputExpanded = expanded) }
    }
    fun onHeartClicked(
        dataCollection: DataCollection,
        itemId: Int,
        configScreenViewModel: ConfigScreenViewModel
    ) {
        when (dataCollection) {
            DataCollection.PRAYS -> {
                if (itemId in lovedDataUiState.value.lovedIdPrays) {
                    val pray = praysList.first { it.id == itemId }

                    updateState {
                        it.copy(lovedIdPrays = it.lovedIdPrays - itemId, lovedPrays = it.lovedPrays - pray)
                    }
                    configScreenViewModel.saveConfiguration(
                        ConfigEntry.FavoritePrays,
                        lovedDataUiState.value.lovedIdPrays
                    )
                }
            }
            DataCollection.SONGS -> {
                if (itemId in lovedDataUiState.value.lovedIdSongs) {
                    val song = songsList.first { it.id == itemId }
                    updateState {
                        it.copy(lovedIdSongs = it.lovedIdSongs - itemId, lovedSongs = it.lovedSongs - song)
                    }
                    configScreenViewModel.saveConfiguration(
                        ConfigEntry.FavoriteSongs,
                        lovedDataUiState.value.lovedIdSongs
                    )
                }
            }
        }
    }

    fun onSearchPrayOrSong(searchValue: String) = updateState { it.copy(searchValue = searchValue) }

    fun onLoad(configViewModel: ConfigScreenViewModel) {
        val defaultConfigurations = configViewModel.loadConfigurations()
        val lovedIdSongs = defaultConfigurations.favoriteSongs
        val lovedIdPrays = defaultConfigurations.favoritePrays
        val lovedSongs = mutableListOf<Song>()
        val lovedPrays = mutableListOf<Pray>()

        lovedIdSongs.forEach { id ->
            songsList
                .firstOrNull { it.id == id }
                ?.let { song -> lovedSongs.add(song) }
        }

        lovedIdPrays.forEach { id ->
            praysList
                .firstOrNull { it.id == id }
                ?.let { pray -> lovedPrays.add(pray) }
        }

        updateState {
            it.copy(
                lovedIdSongs = lovedIdSongs,
                lovedIdPrays = lovedIdPrays,
                lovedSongs = lovedSongs,
                lovedPrays = lovedPrays
            )
        }

        isLoading = false
    }
}