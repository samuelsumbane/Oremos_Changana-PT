package com.samuel.oremoschanganapt.presentation.lovedData

//import com.samuel.oremoschanganapt.ui_core.ColorObject
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuel.oremoschanganapt.BottomNav
import com.samuel.oremoschanganapt.createSettings
import com.samuel.oremoschanganapt.domain.DataCollection
import com.samuel.oremoschanganapt.domain.isDesktop
import com.samuel.oremoschanganapt.domain.isNumber
import com.samuel.oremoschanganapt.presentation.ConfigScreenViewModel
import com.samuel.oremoschanganapt.searchWidget
import com.samuel.oremoschanganapt.ui_core.ColorObject
import com.samuel.oremoschanganapt.ui_core.PageName
import com.samuel.oremoschanganapt.ui_core.globalComponents.DataNotFound
import com.samuel.oremoschanganapt.ui_core.globalComponents.LoadingScreen
import com.samuel.oremoschanganapt.ui_core.globalComponents.PrayRow
import com.samuel.oremoschanganapt.ui_core.globalComponents.SongRow
import com.samuel.oremoschanganapt.ui_core.globalComponents.lazyColumn
import com.samuel.oremoschanganapt.ui_core.states.AppState.isLoading
import kotlinx.coroutines.launch
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.arrow_back
import oremoschangana.composeapp.generated.resources.loved
import oremoschangana.composeapp.generated.resources.prays
import oremoschangana.composeapp.generated.resources.songs
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


object LovedDataScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        LovedDataPage(navigator)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LovedDataPage(navigator: Navigator) {
    val lovedDataViewModel = koinViewModel<LovedDataViewModel>()
    val lovedDataUiState by lovedDataViewModel.lovedDataUiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val configViewModel = remember { ConfigScreenViewModel(createSettings()) }


    LaunchedEffect(Unit) {
        lovedDataViewModel.onLoad(configViewModel)
    }

    if (isLoading) {
        LoadingScreen()
    } else {
        val filteredPrays = remember(lovedDataUiState.lovedPrays, lovedDataUiState.searchValue) {
            if (lovedDataUiState.searchValue.isNotEmpty()) {
                lovedDataUiState.lovedPrays.filter { it.title.contains(lovedDataUiState.searchValue, ignoreCase = true) }
            } else lovedDataUiState.lovedPrays
        }

        val filteredSongs = remember(lovedDataUiState.lovedSongs, lovedDataUiState.searchValue) {
            if (lovedDataUiState.searchValue.isNotBlank()) {
                val numOrNot = isNumber(lovedDataUiState.searchValue)
                if (numOrNot) {
                    lovedDataUiState.lovedSongs.filter { it.number == lovedDataUiState.searchValue }
                } else {
                    lovedDataUiState.lovedSongs.filter {
                        it.title.contains(lovedDataUiState.searchValue, ignoreCase = true)
                    }
                }
            } else lovedDataUiState.lovedSongs
        }

        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val tabs = listOf(
            stringResource(Res.string.songs), stringResource(Res.string.prays),
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        if (!lovedDataUiState.searchInputExpanded) {
                            Text(
                                text = stringResource(Res.string.loved),
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
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
                        searchWidget(
                            searchInputLabel = if (selectedTabIndex == 1) "Pesquisar oração favorita" else "Pesquisar cântico favorito",
                            onExpand = { lovedDataViewModel.onEvent(LovedDataUiEvents.OnExpandSearchInput(it)) }
                        ) { song ->
                            lovedDataViewModel.onSearchPrayOrSong(song)
                        }
                    }
                )
            },
            bottomBar = {
                BottomNav(navigator, PageName.MOREPAGES.value)
            }
        ) { paddingVales ->
            Column(
                Modifier
                    .padding(paddingVales)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                @Composable
                fun tabContent(dataCollection: DataCollection) {
                    if (dataCollection == DataCollection.PRAYS) {
                        if (filteredPrays.isEmpty()) {
                            DataNotFound(text = "Nenhuma oração encontrada nos favoritos.")
                        } else {
                            lazyColumn {
                                items(filteredPrays) { pray ->
                                    PrayRow(
                                        navigator,
                                        pray = pray,
                                        loved = pray.id in lovedDataUiState.lovedIdPrays,
                                        onToggleLoved = { id ->
                                            lovedDataViewModel
                                                .onEvent(LovedDataUiEvents
                                                    .OnHeartClicked(DataCollection.PRAYS, id, configViewModel)
                                                )
                                        })
                                }
                            }
                        }
                    } else {
                        if (filteredSongs.isEmpty()) {
                            DataNotFound(text = "Nenhum cântico encontrado nos favoritos.")
                        } else {
                            lazyColumn {
                                items(filteredSongs) { song ->
                                    SongRow(
                                        navigator,
                                        modifier = Modifier,
                                        song,
                                        loved = song.id in lovedDataUiState.lovedIdSongs,
                                        onToggleLoved = { id ->
                                            coroutineScope.launch {
                                                lovedDataViewModel
                                                    .onEvent(LovedDataUiEvents
                                                        .OnHeartClicked(DataCollection.SONGS, id, configViewModel)
                                                    )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Column(
                    Modifier
                        .fillMaxWidth(if (isDesktop()) 0.5f else 1f)
//                    .background(Color.Red)
                ) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier.padding(10.dp).background(Color.Gray)
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            Tab(
                                text = {
                                    Text(
                                        text = tab,
                                        style = typography.bodyMedium,
                                        fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                                        color = if (selectedTabIndex == index) Color.White else MaterialTheme.colorScheme.tertiary
                                    )
                                },
                                selected = selectedTabIndex == index,
                                onClick = {
                                    selectedTabIndex = index
                                },
                                modifier = Modifier
                                    .background(
                                        color = if (selectedTabIndex == index) ColorObject.mainColor else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp),
                                    ),
                                selectedContentColor = ColorObject.mainColor,
                            )
                        }
                    }

                    AnimatedContent(
                        targetState = selectedTabIndex,
                        transitionSpec = {
                            slideIntoContainer(
                                animationSpec = tween(400, easing = EaseIn), towards = Up
                            ).togetherWith(
                                slideOutOfContainer(
                                    animationSpec = tween(450, easing = EaseOut), towards = Down
                                )
                            )
                        },
                    ) { selectedTabIndex ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            when (selectedTabIndex) {
                                0 -> tabContent(DataCollection.SONGS)
                                1 -> tabContent(DataCollection.PRAYS)
                            }
                        }
                    }
                }
            }
        }
    }
}
