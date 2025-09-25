package com.samuelsumbane.oremoscatolico.view

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuelsumbane.oremoscatolico.commonView.CommonAgroupedPage
//import com.samuelsumbane.oremoscatolico.commonView.DesktopPraysPage

//object DesktopPraysScreen : Screen {
//    @Composable
//    override fun Content() {
//        val navigator = LocalNavigator.currentOrThrow
//        DesktopPraysPage(navigator)
//    }
//}

object DesktopAgrouppedScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        CommonAgroupedPage(navigator)
    }
}

object DesktopmorePagesPage : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
//        MorePages(navigator)
    }
}

