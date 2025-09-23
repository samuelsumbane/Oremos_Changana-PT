package com.samuelsumbane.oremoscatolico.commonView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuelsumbane.oremoscatolico.AditionalVerticalScroll
import com.samuelsumbane.oremoscatolico.ApendixScreen
import com.samuelsumbane.oremoscatolico.BottomNav
import com.samuelsumbane.oremoscatolico.CommonAboutAppScreen
//import com.samuelsumbane.oremoscatolico.CommonSideBarScreen
import com.samuelsumbane.oremoscatolico.FestasMoveisScreen
import com.samuelsumbane.oremoscatolico.LicionarioScreen
import com.samuelsumbane.oremoscatolico.RemindersScreen
import com.samuelsumbane.oremoscatolico.SantoralScreen
import com.samuelsumbane.oremoscatolico.globalComponents.AppSideBar
import com.samuelsumbane.oremoscatolico.globalComponents.MorePagesBtn
import com.samuelsumbane.oremoscatolico.repository.isAndroid
import com.samuelsumbane.oremoscatolico.repository.isDesktop
import com.samuelsumbane.oremoscatolico.repository.PageName
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.about
import oremoscatolico.composeapp.generated.resources.appendix
import oremoscatolico.composeapp.generated.resources.cruz
import oremoscatolico.composeapp.generated.resources.date_range
import oremoscatolico.composeapp.generated.resources.more_pages
import oremoscatolico.composeapp.generated.resources.notifications
import oremoscatolico.composeapp.generated.resources.party
import oremoscatolico.composeapp.generated.resources.settings
import oremoscatolico.composeapp.generated.resources.star
import org.jetbrains.compose.resources.stringResource


object MorePagesScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        MorePages(navigator)
    }
}

@Composable
fun MorePages(navigator: Navigator, ) {
//    val configuration = LocalConfiguration.current
//    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    val curvePercent = 16
    val noCurve = 6

    Scaffold(
        bottomBar = { BottomNav(navigator, PageName.MOREPAGES.value) }
    ) { paddingValues ->
        Row(Modifier
            .fillMaxSize()
            .padding(paddingValues)
//            .background(Color.Green)
        ) {
//            CommonSideBarScreen(PageName.PRAYS.value)
            if (isDesktop()) AppSideBar(navigator, PageName.PRAYS.value)
            val scrollState = rememberScrollState()

            Column(
                Modifier
                    .weight(1f)
//                    .background(MaterialTheme.colorScheme.background)
//                    .background(Color.Red)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(Res.string.more_pages), fontSize = 24.sp, fontStyle = FontStyle.Italic, modifier = Modifier.padding(top = 40.dp, bottom = 30.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(fraction = if (isAndroid()) 0.80f else 0.40f),
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    MorePagesButtonRow {
                        MorePagesBtn(
                            icon = Res.drawable.appendix,
                            description = "pagina de apêndice",
                            text = "Apêndice",
                            Modifier.weight(1f),
                            shape = RoundedCornerShape(curvePercent,  noCurve,  noCurve,  noCurve)
                        ) {
                            navigator.push(ApendixScreen)
                        }

                        Spacer(Modifier.width(25.dp))

                        MorePagesBtn(
                            icon = Res.drawable.party,
                            description = "Pagina de festas móveis",
                            text = "Festas Móveis",
                            Modifier.weight(1f),
                            shape = RoundedCornerShape(noCurve, curvePercent, noCurve, noCurve)
                        ) {
                            navigator.push(FestasMoveisScreen)
                        }
                    }

                    MorePagesButtonRow {
                        MorePagesBtn(
                            icon = Res.drawable.date_range,
                            description = "Pagina de liccionario",
                            text = "Leccionário",
                            Modifier.weight(1f),
                            shape = RoundedCornerShape(noCurve, noCurve, noCurve, curvePercent)
                        ) {
//                            navigator.navigate("licionario")
                            navigator.push(LicionarioScreen)
                        }
                        Spacer(Modifier.width(25.dp))

                        MorePagesBtn(
                            icon = Res.drawable.cruz,
                            description = "pagina de santos e santas",
                            text = "Santoral",
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(noCurve, noCurve, curvePercent, noCurve)
                        ) {
//                            navigator.navigate("santoralpage")
                            navigator.push(SantoralScreen)
                        }
                    }

                    Spacer(Modifier.width(25.dp))

                    if (isAndroid()) {
                        MorePagesButtonRow {
                            MorePagesBtn(
                                icon = Res.drawable.notifications,
                                description = "pagina de lembretes",
                                text = "Lembretes",
                                Modifier.height(100.dp).weight(1f),
                                shape = RoundedCornerShape(curvePercent)
                            ) {
                                navigator.push(RemindersScreen)
                            }

                            Spacer(Modifier.width(25.dp))

                            MorePagesBtn(
                                icon = Res.drawable.settings,
                                description = "",
                                text = "Configurações",
                                Modifier
                                    .height(100.dp)
                                    .weight(1f),
                                shape = RoundedCornerShape(curvePercent)
                            ) { navigator.push(CommonSettingsScreen) }

                        }
                    }

                    MorePagesButtonRow {
                        MorePagesBtn(
                            icon = Res.drawable.star,
                            description = "Pagina de orações e cânticos favoritos",
                            text = "Favoritos",
                            Modifier.height(100.dp).weight(1f),
                            shape = RoundedCornerShape(curvePercent)
                        ) { navigator.push(LovedDataScreen) }

                                Spacer(Modifier.width(25.dp))
                            MorePagesBtn(
                                icon = null,
                                description = "",
                                text = stringResource(Res.string.about),
                                Modifier
                                    .height(100.dp)
                                    .weight(1f),
                                shape = RoundedCornerShape(curvePercent)
                            ) { navigator.push(CommonAboutAppScreen) }
                    }
                }
            }
            if (isDesktop()) AditionalVerticalScroll(modifier = Modifier, null, scrollState)

//            ShortcutsButton(navigator)
        }

    }
}


@Composable
fun MorePagesButtonRow(content: @Composable () -> Unit) {
    Row(Modifier.widthIn(max = 450.dp).fillMaxWidth()) {
        content()
    }
}
