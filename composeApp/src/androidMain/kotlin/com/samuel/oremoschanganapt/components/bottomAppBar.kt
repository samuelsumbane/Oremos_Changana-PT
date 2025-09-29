package com.samuel.oremoschanganapt.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.globalComponents.MenuContent
import com.samuel.oremoschanganapt.globalComponents.appRouter
//import com.samuel.oremoschanganapt.repository.ColorObject


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
                .fillMaxSize()
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

