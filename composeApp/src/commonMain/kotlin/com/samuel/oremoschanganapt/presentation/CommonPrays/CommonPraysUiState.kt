package com.samuel.oremoschanganapt.presentation.CommonPrays

import com.samuel.oremoschanganapt.ui_core.globalComponents.Pray

data class CommonPraysUiState(
    val searchValue: String = "",
    val allPrays: List<Pray> = emptyList(),
    val lovedIdPrays: Set<Int> = emptySet(),
)
