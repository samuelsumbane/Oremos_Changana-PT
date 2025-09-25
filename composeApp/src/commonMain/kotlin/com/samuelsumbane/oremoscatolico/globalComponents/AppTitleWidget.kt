package com.samuelsumbane.oremoscatolico.globalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.samuelsumbane.oremoscatolico.CommonAboutAppScreen

@Composable
fun AppTitleWidget(navigator: Navigator) {
    Column(
        Modifier
            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12))
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color.Black.copy(alpha = 0.2f),
                spotColor = Color.Transparent
            )
            .clickable { navigator.push(CommonAboutAppScreen) }
    ) {
        HomeTexts(text = "Oremos", fontSize = 45)
        Spacer(modifier = Modifier.height(9.dp))
        HomeTexts(text = "A HI KHONGELENI", fontSize = 23)
    }
}