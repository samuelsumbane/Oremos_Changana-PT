package com.samuel.oremoschanganapt.ui_core.globalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.samuel.oremoschanganapt.ui_core.ColorObject
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MorePagesBtn(
    icon: DrawableResource?,
    description: String,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentPadding = PaddingValues(15.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = description,
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(24.dp),
                    tint = ColorObject.mainColor
                )
            } ?: run {
                Spacer(Modifier.padding(end = 44.dp))
            }
            Text(
                text = text,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = textFontSize(),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun MorePagesColumn(
    content: @Composable () -> Unit
) {
    val columnBackground = if (isSystemInDarkTheme()) Color(34, 38, 46) else Color(235, 235, 235)
    Column(
        modifier = Modifier
            .padding(12.dp)
            .background(columnBackground, RoundedCornerShape(16.dp))
            .padding(0.dp, 10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        content()
    }
}

@Composable
fun ButtonsDivider() {
    HorizontalDivider(
        modifier = Modifier
            .padding(20.dp, 0.dp)
            .fillMaxWidth(),
        thickness = 0.5.dp,
        color = Color.Black.copy(alpha = 0.2f)
    )
}