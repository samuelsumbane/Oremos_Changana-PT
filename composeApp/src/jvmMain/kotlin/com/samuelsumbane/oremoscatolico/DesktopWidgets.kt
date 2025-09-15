package com.samuelsumbane.oremoscatolico

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ConfigColumn(
    title: String,
    content: @Composable () -> Unit
) {
    Spacer(modifier = Modifier.height(30.dp))

    Column {
        Text(text = title, fontSize = 15.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        content()
    }
}

