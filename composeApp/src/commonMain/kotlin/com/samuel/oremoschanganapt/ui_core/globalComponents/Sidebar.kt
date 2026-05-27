package com.samuel.oremoschanganapt.ui_core.globalComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.isMobilePortrait
import com.samuel.oremoschanganapt.domain.isAndroid
import com.samuel.oremoschanganapt.domain.isDesktop


fun Modifier.sidebarHeight(): Modifier {
    return if (isDesktop()) {
        this.height(400.dp)
    } else {
        this.fillMaxHeight()
    }
}

@Composable
fun AppSideBar(navigator: Navigator, activePage: String = "home") {
    if ((isAndroid() && !isMobilePortrait()) || isDesktop()) {
        Column(
            modifier = Modifier
                .width(80.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            SidebarNav(
                activePage,
                onEachBtnClicked = { page ->
                    appRouter(navigator, page)
                }
            )
        }
    }
}