package com.samuel.oremoschanganapt.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.samuel.oremoschanganapt.repository.FontSizeName

object UIState {
    var appMode by mutableStateOf(false)
    var themeMode by mutableStateOf("")

    var configFontSize by mutableStateOf(FontSizeName.NORMAL.string)
    var isFullScreen by mutableStateOf(false)
    var isSnackbarVisible by  mutableStateOf(true)
}