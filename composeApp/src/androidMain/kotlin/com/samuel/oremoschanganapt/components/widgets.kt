package com.samuel.oremoschanganapt.components

import android.content.Context
import android.widget.Toast

fun toastAlert(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT){
    val toast = Toast.makeText(context, text, duration)
    toast.show()
}
