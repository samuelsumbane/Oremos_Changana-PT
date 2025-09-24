package com.samuelsumbane.oremoscatolico.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.samuelsumbane.oremoscatolico.repository.FontSize

object UIState {
    var appMode by mutableStateOf(false)
    var themeMode by mutableStateOf("")

    var configFontSize by mutableStateOf(FontSize.NORMAL.string)
    var isFullScreen by mutableStateOf(false)
    var isSnackbarVisible by  mutableStateOf(true)
}