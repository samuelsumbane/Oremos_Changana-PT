package com.samuel.oremoschanganapt.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppState {
//    var configSongNumber by mutableIntStateOf(0)
    var isSearchInputVisible by mutableStateOf(false)
    var isContainerActive by mutableStateOf(false)
    var isLoading by mutableStateOf(true)

}