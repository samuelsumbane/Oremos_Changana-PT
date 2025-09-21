package com.samuel.oremoschanganapt.view.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.samuelsumbane.oremoscatolico.repository.FontSize

object UIState {
    var configFontSize by mutableStateOf(FontSize.NORMAL.string)
    var isFullScreen by mutableStateOf(false)
    var isSnackbarVisible by  mutableStateOf(true)
}