package com.samuel.oremoschanganapt.repository

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class FontSize(val size: TextUnit) {
    SMALL(12.sp),
    NORMAL(16.sp),
    LARGE(20.sp);

    companion object {
        fun fromString(value: String): FontSize {
            return when (value) {
                "Small" -> SMALL
                "Large" -> LARGE
                else -> NORMAL  // Default to "normal"
            }
        }
    }
}
