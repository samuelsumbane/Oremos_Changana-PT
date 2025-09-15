package com.samuelsumbane.oremoscatolico.view.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.samuelsumbane.oremoscatolico.R

object AppState {
//    var configSongNumber by mutableIntStateOf(0)
    var isSearchInputVisible by mutableStateOf(false)
    var isContainerActive by mutableStateOf(false)
    var isLoading by mutableStateOf(true)

}