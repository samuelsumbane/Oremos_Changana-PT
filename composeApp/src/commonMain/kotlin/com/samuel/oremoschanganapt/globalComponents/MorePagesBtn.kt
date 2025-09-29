package com.samuel.oremoschanganapt.globalComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.samuel.oremoschanganapt.repository.ColorObject
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MorePagesBtn(
    icon: DrawableResource?,
    description: String,
    text: String,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorObject.mainColor
        ),
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 12.dp, shape = shape, spotColor = Color.Black)
            .height(120.dp),
        shape = shape,
        contentPadding = PaddingValues(15.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            icon?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = description,
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text, color = Color.White, fontSize = textFontSize(), fontWeight = FontWeight.SemiBold)
        }
    }
}
