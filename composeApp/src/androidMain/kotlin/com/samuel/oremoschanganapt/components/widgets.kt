package com.samuel.oremoschanganapt.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

fun toastAlert(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT){
    val toast = Toast.makeText(context, text, duration)
    toast.show()
}
