package com.samuel.oremoschanganapt.globalComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.samuel.oremoschanganapt.repository.ColorObject

@Composable
fun CancelButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = ColorObject.mainColor, containerColor = Color.Transparent
        ),
    ) {
        Text(text = text)
    }
}

@Composable
fun ReminderButton(
    text: String,
    onClick: () -> Unit
) {
    val mTheme = MaterialTheme.colorScheme
    Button(
        onClick = onClick,
        modifier = Modifier.width(95.dp).height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = mTheme.background.copy(alpha = 0.7f),
            contentColor = mTheme.tertiary
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(11.dp),
    ) {
        Text(text, color = MaterialTheme.colorScheme.tertiary)
    }
}


@Composable
fun submitButtonsRow(
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        content()
    }
}



@Composable
fun NormalButton(
    text: String,
    btnColor: Color = ColorObject.mainColor,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.width(95.dp).height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = btnColor,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp),
//        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text, color = Color.White)
    }
}
