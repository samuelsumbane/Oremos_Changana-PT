package com.samuelsumbane.oremoscatolico.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AlertViewModel : ViewModel() {
    private val _snackbarMessage = mutableStateOf<String?>(null)
    val snackbarMessage: MutableState<String?>
        get() = _snackbarMessage

    fun showSnackbar(message: String) {
        _snackbarMessage.value = message
    }

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }
}