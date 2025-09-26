package com.samuelsumbane.oremoscatolico.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuelsumbane.oremoscatolico.R
import com.samuelsumbane.oremoscatolico.globalComponents.MenuContent
import com.samuelsumbane.oremoscatolico.globalComponents.appRouter
//import com.samuelsumbane.oremoscatolico.repository.ColorObject
import com.samuelsumbane.oremoscatolico.ui.theme.DarkSecondary
import com.samuelsumbane.oremoscatolico.ui.theme.LightSecondary


@Composable
fun BottomAppBarPrincipal(
    navigator: Navigator,
    activePage: String,
    iconColorState: String = "Keep",
) {
    BottomAppBar(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(Color.Transparent)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.Transparent)
                .padding(7.dp, 0.dp, 7.dp, 7.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MenuContent(activePage, iconColorState, onMenuBtnClicked = { page ->
                    appRouter(navigator, page)
                })
            }
        }
    }
}

