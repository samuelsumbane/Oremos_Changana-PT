package com.samuel.oremoschanganapt.presentation.CommonPrays

import androidx.lifecycle.ViewModel
import com.samuel.oremoschanganapt.presentation.ConfigEntry
import com.samuel.oremoschanganapt.presentation.ConfigScreenViewModel
import com.samuel.oremoschanganapt.ui_core.globalComponents.Pray
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CommonPraysViewModel : ViewModel() {
    private val _state = MutableStateFlow(CommonPraysUiState())
    val commonPraysUiState = _state.asStateFlow()

    fun updateState(block: (CommonPraysUiState) -> CommonPraysUiState) = _state.update(block)

    fun onEvent(commonPrayUiEvent: CommonPraysUiEvents) {
        when (commonPrayUiEvent) {
            is CommonPraysUiEvents.OnHeartClicked -> changePraysLovedState(commonPrayUiEvent.prayId, commonPrayUiEvent.configViewModel)
            is CommonPraysUiEvents.OnSetPraysList -> onSetPraysList(commonPrayUiEvent.prays)
            is CommonPraysUiEvents.OnSearchPray -> onSearchPray(commonPrayUiEvent.prayName)
        }
    }

    fun onSetPraysList(newPrays: List<Pray>) {
        updateState { it.copy(allPrays = newPrays) }
    }

    fun changePraysLovedState(prayId: Int, configScreenViewModel: ConfigScreenViewModel) {
        updateState { it.copy(lovedIdPrays =
            if (prayId in commonPraysUiState.value.lovedIdPrays) it.lovedIdPrays - prayId else it.lovedIdPrays + prayId
        ) }

        configScreenViewModel.saveConfiguration(
            ConfigEntry.FavoritePrays, commonPraysUiState.value.lovedIdPrays
        )
    }

    fun onSearchPray(prayName: String) = updateState { it.copy(searchValue = prayName) }
    fun onSetLovedPrays(lovedPrays: Set<Int>) = updateState { it.copy(lovedIdPrays = lovedPrays) }

}