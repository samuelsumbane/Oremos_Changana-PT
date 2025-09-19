package com.samuelsumbane.oremoscatolico.commonView

//import com.samuelsumbane.oremoscatolico.globalComponents.showSnackbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.samuelsumbane.oremoscatolico.AditionalVerticalScroll
import com.samuelsumbane.oremoscatolico.BottomNav
import com.samuelsumbane.oremoscatolico.createSettings
import com.samuelsumbane.oremoscatolico.data.praysData
import com.samuelsumbane.oremoscatolico.globalComponents.AppSideBar
import com.samuelsumbane.oremoscatolico.globalComponents.LoadingScreen
import com.samuelsumbane.oremoscatolico.globalComponents.PrayRow
import com.samuelsumbane.oremoscatolico.globalComponents.ScrollToFirstItemBtn
import com.samuelsumbane.oremoscatolico.globalComponents.platformWidth
import com.samuelsumbane.oremoscatolico.isAndroid
import com.samuelsumbane.oremoscatolico.isDesktop
import com.samuelsumbane.oremoscatolico.repository.PageName
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigEntry
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigScreenViewModel
import kotlinx.coroutines.launch
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.prays
import org.jetbrains.compose.resources.stringResource


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

    var searchValue by remember { mutableStateOf("") }
    val allPrays by remember { mutableStateOf(praysData) }

    var lovedIdPrays by remember { mutableStateOf(setOf<Int>()) }
//    val alertViewModel = AlertViewModel()

//    LaunchedEffect(Unit) {
//        lovedIdPrays = getIdSet(context, SetIdPreference.PRAYS_ID.preferenceName)
//    }
      val configViewModal = remember { ConfigScreenViewModel(createSettings()) }

    LaunchedEffect(Unit) {
        val defaultConfig = configViewModal.loadConfigurations()
        lovedIdPrays = defaultConfig.favoritePrays
//                    println("The prays are ${lovedIdPrays}")
    }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = stringResource(Res.string.prays), color = MaterialTheme.colorScheme.tertiary)},
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent
                            ),
                            navigationIcon = {
//                                IconButton(onClick = { navigator.pop() } )
                            },
                            actions = {
//                                DesktopSearchContainer {
//                                    searchValue = it
//                                }
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
                        allPrays.isEmpty() -> LoadingScreen()
                        else -> {
                            val filteredPrays = remember(allPrays, searchValue){
                                if (searchValue.isNotEmpty()) {
                                    allPrays.filter { it.title.contains(searchValue, ignoreCase = true)}
                                } else {
                                    allPrays
                                }
                            }

                            Row(Modifier.fillMaxSize().padding(paddingVales)) {
                                if (isDesktop()) AppSideBar(navigator, PageName.PRAYS.value)

                                Row(
                                    modifier = Modifier
                                        .padding(end = 12.dp)
                                        .weight(1f)
                                    ,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    LazyColumn(
                                        state = listState,
                                        modifier = Modifier
                                            .padding(top = 30.dp, end = 8.dp, bottom = 8.dp, start = 8.dp)
//                                            .fillMaxSize(0.97f)
                                            .platformWidth(),
//                                            .align(Alignment.CenterHorizontally)
//                                            .fillMaxWidth(0.5f),
                                        verticalArrangement = Arrangement.spacedBy(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally

                                    ) {
                                        items (filteredPrays) { pray ->
                                            // Each pray row --------->>
                                            PrayRow(
                                                navigator,
                                                modifier = Modifier.platformWidth(),
                                                pray = pray,
                                                loved = pray.id in lovedIdPrays,
                                                onToggleLoved = { id ->
                                                    coroutineScope.launch {
                                                        if (id in lovedIdPrays) {
                                                            lovedIdPrays -= id
                                                        } else {
                                                            lovedIdPrays += id
                                                        }

                                                        configViewModal.saveConfiguration(
                                                            ConfigEntry.FavoritePrays, lovedIdPrays
                                                        )

                                                    }
                                                }
                                            )
                                        }
                                    }
                                }

                                if (isAndroid()) {
                                    if (showUpButton) {
                                        ScrollToFirstItemBtn(modifier = Modifier
                                            //                                            .align(alignment = Alignment.BottomEnd)
                                            .background(Color.Red)
                                            //                                            .align(alignment = Alignment.Bottom)
                                        ) {
                                            coroutineScope.launch { listState.scrollToItem(0) }
                                        }
                                    }
                                } else {
                                    AditionalVerticalScroll(lazyListState = listState, scrollState = null)
                                    Spacer(Modifier.width(5.dp))
                                }
                            }

//                ShortcutsButton(navigator)
                        }
                    }
                }

}