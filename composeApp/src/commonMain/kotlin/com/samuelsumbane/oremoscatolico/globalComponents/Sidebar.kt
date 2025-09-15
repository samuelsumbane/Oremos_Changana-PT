package com.samuelsumbane.oremoscatolico.globalComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun AppSideBar(navigator: Navigator, activePage: String = "home") {
    Column(
        modifier = Modifier
            .width(80.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .height(400.dp)
//                .width(90.dp)
                .fillMaxWidth()
        ) {
            SidebarNav(
                activePage,
                modifier = Modifier.fillMaxSize(),
                onEachBtnClicked = { page ->
                    appRouter(navigator, page)
                }
            )
        }
    }
}