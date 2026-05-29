package com.samuel.oremoschanganapt.presentation.CommonPrays

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuel.oremoschanganapt.AditionalVerticalScroll
import com.samuel.oremoschanganapt.BottomNav
import com.samuel.oremoschanganapt.createSettings
import com.samuel.oremoschanganapt.ui_core.globalComponents.AppSideBar
import com.samuel.oremoschanganapt.ui_core.globalComponents.LoadingScreen
import com.samuel.oremoschanganapt.ui_core.globalComponents.PrayRow
import com.samuel.oremoschanganapt.ui_core.globalComponents.ScrollToFirstItemBtn
import com.samuel.oremoschanganapt.ui_core.globalComponents.platformWidth
import com.samuel.oremoschanganapt.praysList
import com.samuel.oremoschanganapt.domain.isDesktop
import com.samuel.oremoschanganapt.searchWidget
import com.samuel.oremoschanganapt.shortcutButtonWidget
import com.samuel.oremoschanganapt.presentation.ConfigScreenViewModel
import com.samuel.oremoschanganapt.ui_core.PageName
import kotlinx.coroutines.launch
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.arrow_back
import oremoschangana.composeapp.generated.resources.prays
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


object PraysScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        CommonPraysPage(navigator)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonPraysPage(navigator: Navigator) {

    val commonPraysViewModel = koinViewModel<CommonPraysViewModel>()
    val commonPraysUiState by commonPraysViewModel.commonPraysUiState.collectAsState()

//    var lovedIdPrays by remember { mutableStateOf(setOf<Int>()) }
    val configViewModal = remember { ConfigScreenViewModel(createSettings()) }

    LaunchedEffect(Unit) {
        val defaultConfig = configViewModal.loadConfigurations()
        commonPraysViewModel.onSetPraysList(praysList)
        commonPraysViewModel.onSetLovedPrays(defaultConfig.favoritePrays)
    }

    Row {
        AppSideBar(navigator, PageName.PRAYS.value)
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.prays),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                painterResource(Res.drawable.arrow_back),
                                contentDescription = "Go back"
                            )
                        }
                    },
                    actions = {
                        searchWidget { prayName -> commonPraysViewModel.onEvent(CommonPraysUiEvents.OnSearchPray(prayName)) }
                    }
                )
            },
            bottomBar = { BottomNav(navigator, PageName.PRAYS.value) }
        ) { paddingVales ->
            val coroutineScope = rememberCoroutineScope()
            val listState = rememberLazyListState()
            val showUpButton by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex > 0
                }
            }

            when {
                commonPraysUiState.allPrays.isEmpty() -> LoadingScreen()
                else -> {
                    val filteredPrays = remember(commonPraysUiState.allPrays, commonPraysUiState.searchValue) {
                        if (commonPraysUiState.searchValue.isNotEmpty()) {
                            commonPraysUiState.allPrays.filter { it.title.contains(commonPraysUiState.searchValue, ignoreCase = true) }
                        } else commonPraysUiState.allPrays
                    }

                    Box(Modifier.fillMaxSize().padding(paddingVales)) {
                        Row(
                            modifier = Modifier
                                .padding(end = if (isDesktop()) 12.dp else 0.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            LazyColumn(
                                state = listState,
                                modifier = Modifier
                                    .padding(top = 30.dp, end = 8.dp, bottom = 8.dp, start = 8.dp)
                                    .platformWidth(),
//                                            .align(Alignment.CenterHorizontally)
//                                            .fillMaxWidth(0.5f),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                items(filteredPrays) { pray ->
                                    // Each pray row --------->>
                                    PrayRow(
                                        navigator,
                                        modifier = Modifier.platformWidth(1f),
                                        pray = pray,
                                        loved = pray.id in commonPraysUiState.lovedIdPrays,
                                        onToggleLoved = { id ->
                                            coroutineScope.launch {
                                                commonPraysViewModel.onEvent(CommonPraysUiEvents.OnHeartClicked(id, configViewModal))
                                            }
                                        }
                                    )
                                }
                            }
                        }

                        if (isDesktop()) {
                            AditionalVerticalScroll(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 10.dp),
                                lazyListState = listState,
                                scrollState = null
                            )
                        }

                        shortcutButtonWidget(navigator)

                        if (showUpButton) {
                            ScrollToFirstItemBtn(
                                modifier = Modifier.align(alignment = Alignment.BottomEnd)
                            ) {
                                coroutineScope.launch {
                                    listState.scrollToItem(0)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}