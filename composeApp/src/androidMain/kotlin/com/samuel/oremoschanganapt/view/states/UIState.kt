package com.samuel.oremoschanganapt.view.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

object UIState {
    var configFontSize by mutableStateOf("Normal")
    var isFullScreen by  mutableStateOf(false)
}