package com.samuel.oremoschanganapt.ui_core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import com.samuel.oremoschanganapt.AditionalVerticalScroll
import com.samuel.oremoschanganapt.ApendixScreen
import com.samuel.oremoschanganapt.BottomNav
import com.samuel.oremoschanganapt.CommonAboutAppScreen
//import com.samuel.oremoschanganapt.CommonSideBarScreen
import com.samuel.oremoschanganapt.FestasMoveisScreen
import com.samuel.oremoschanganapt.LicionarioScreen
import com.samuel.oremoschanganapt.RemindersScreen
import com.samuel.oremoschanganapt.SantoralScreen
import com.samuel.oremoschanganapt.ui_core.globalComponents.AppSideBar
import com.samuel.oremoschanganapt.ui_core.globalComponents.MorePagesBtn
import com.samuel.oremoschanganapt.domain.isAndroid
import com.samuel.oremoschanganapt.domain.isDesktop
import com.samuel.oremoschanganapt.presentation.lovedData.LovedDataScreen
import com.samuel.oremoschanganapt.ui_core.globalComponents.ButtonsDivider
import com.samuel.oremoschanganapt.ui_core.globalComponents.MorePagesColumn
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.about
import oremoschangana.composeapp.generated.resources.appendix
import oremoschangana.composeapp.generated.resources.cruz
import oremoschangana.composeapp.generated.resources.date_range
import oremoschangana.composeapp.generated.resources.heart
import oremoschangana.composeapp.generated.resources.more_pages
import oremoschangana.composeapp.generated.resources.notifications
import oremoschangana.composeapp.generated.resources.party
import oremoschangana.composeapp.generated.resources.settings
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

    Row {
        AppSideBar(navigator, PageName.MOREPAGES.value)

        Scaffold(
            bottomBar = { BottomNav(navigator, PageName.MOREPAGES.value) }
        ) { paddingValues ->
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val scrollState = rememberScrollState()

                Column(
                    Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(Res.string.more_pages),
                        fontSize = 24.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(top = 40.dp, bottom = 30.dp)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(fraction = if (isAndroid()) 0.90f else 0.40f)
                            .fillMaxHeight(),
                    ) {

                        items(1) {
                            MorePagesColumn {
                                MorePagesBtn(
                                    icon = Res.drawable.appendix,
                                    description = "pagina de apêndice",
                                    text = "Apêndice",
                                ) {
                                    navigator.push(ApendixScreen())
                                }

                                ButtonsDivider()

                                MorePagesBtn(
                                    icon = Res.drawable.party,
                                    description = "Pagina de festas móveis",
                                    text = "Festas Móveis",
                                ) {
                                    navigator.push(FestasMoveisScreen())
                                }

                                ButtonsDivider()

                                MorePagesBtn(
                                    icon = Res.drawable.date_range,
                                    description = "Pagina de liccionario",
                                    text = "Leccionário",
                                ) {
                                    navigator.push(LicionarioScreen())
                                }

                                ButtonsDivider()

                                MorePagesBtn(
                                    icon = Res.drawable.cruz,
                                    description = "pagina de santos e santas",
                                    text = "Santoral",
                                ) {
                                    navigator.push(SantoralScreen())
                                }
                            }

                            MorePagesColumn {
                                if (isAndroid()) {
                                    MorePagesBtn(
                                        icon = Res.drawable.notifications,
                                        description = "pagina de lembretes",
                                        text = "Lembretes",
                                    ) {
                                        navigator.push(RemindersScreen())
                                    }

                                    ButtonsDivider()

                                    MorePagesBtn(
                                        icon = Res.drawable.settings,
                                        description = "",
                                        text = "Configurações",
                                    ) { navigator.push(CommonSettingsScreen) }

                                    ButtonsDivider()

                                    MorePagesBtn(
                                        icon = Res.drawable.heart,
                                        description = "Pagina de orações e cânticos favoritos",
                                        text = "Favoritos",
                                    ) { navigator.push(LovedDataScreen) }

                                    ButtonsDivider()

                                    MorePagesBtn(
                                        icon = null,
                                        description = "",
                                        text = stringResource(Res.string.about),
                                    ) { navigator.push(CommonAboutAppScreen) }
                                }
                                Text("")

                            }
                        }
                    }
                }
                if (isDesktop()) AditionalVerticalScroll(modifier = Modifier, null, scrollState)

//            ShortcutsButton(navigator)
            }
        }
    }
}


@Composable
fun MorePagesButtonRow(content: @Composable () -> Unit) {
    Row(Modifier.widthIn(max = 450.dp).fillMaxWidth()) {
        content()
    }
}
