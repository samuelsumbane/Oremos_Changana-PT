package com.samuel.oremoschanganapt.ui_core.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppState {
    var isSearchInputVisible by mutableStateOf(false)
    var isContainerActive by mutableStateOf(false)
    var isLoading by mutableStateOf(true)
}