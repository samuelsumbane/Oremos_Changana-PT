package com.samuel.oremoschanganapt.commonView

//import com.samuel.oremoschanganapt.repository.ColorObject
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.BottomNav
import com.samuel.oremoschanganapt.createSettings
import com.samuel.oremoschanganapt.data.Pray
import com.samuel.oremoschanganapt.data.praysData
import com.samuel.oremoschanganapt.db.data.Song
import com.samuel.oremoschanganapt.db.data.songsData
import com.samuel.oremoschanganapt.globalComponents.LoadingScreen
import com.samuel.oremoschanganapt.globalComponents.PrayRow
import com.samuel.oremoschanganapt.globalComponents.SongRow
import com.samuel.oremoschanganapt.globalComponents.lazyColumn
import com.samuel.oremoschanganapt.repository.DataCollection
import com.samuel.oremoschanganapt.repository.PageName
import com.samuel.oremoschanganapt.repository.isDesktop
import com.samuel.oremoschanganapt.repository.isNumber
import com.samuel.oremoschanganapt.searchWidget
import com.samuel.oremoschanganapt.states.AppState.isLoading
import com.samuel.oremoschanganapt.viewmodels.ConfigEntry
import com.samuel.oremoschanganapt.viewmodels.ConfigScreenViewModel
import kotlinx.coroutines.launch
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.arrow_back
import oremoschangana.composeapp.generated.resources.loved
import oremoschangana.composeapp.generated.resources.prays
import oremoschangana.composeapp.generated.resources.songs
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


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
    var searchValue by remember { mutableStateOf("") }

    var lovedPrays by remember { mutableStateOf(mutableListOf<Pray>()) }
    val lovedSongs by remember { mutableStateOf(mutableListOf<Song>()) }

//    val context = LocalContext.current
    var lovedIdPrays by remember { mutableStateOf(setOf<Int>()) }
    var lovedIdSongs by remember { mutableStateOf(setOf<Int>()) }
    val coroutineScope = rememberCoroutineScope()
    val configViewModel = remember { ConfigScreenViewModel(createSettings()) }

    LaunchedEffect(Unit) {
        val defaultConfigurations = configViewModel.loadConfigurations()
        lovedIdSongs = defaultConfigurations.favoriteSongs
        lovedIdPrays = defaultConfigurations.favoritePrays

        lovedIdSongs.forEach { id ->
            songsData
                .firstOrNull { it.id == id }
                ?.let { song -> lovedSongs.add(song) }
        }
        println("lovedIdSongs : $lovedIdSongs && lovedSongs: $lovedSongs")

        lovedIdPrays.forEach { id ->
            praysData
                .firstOrNull { it.id == id }
                ?.let { pray -> lovedPrays.add(pray) }
        }
        isLoading = false
    }

    if (isLoading) {
        LoadingScreen()
    } else {
        val filteredPrays = remember(lovedPrays, searchValue) {
            if (searchValue.isNotEmpty()) {
                lovedPrays.filter { it.title.contains(searchValue, ignoreCase = true) }
            } else lovedPrays
        }

        val filteredSongs = remember(lovedSongs, searchValue) {
            if (searchValue.isNotBlank()) {
                val numOrNot = isNumber(searchValue)
                if (numOrNot) {
                    lovedSongs.filter { it.number == searchValue }
                } else {
                    lovedSongs.filter {
                        it.title.contains(searchValue, ignoreCase = true)
                    }
                }
            } else lovedSongs
        }

        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val tabs = listOf(
            stringResource(Res.string.songs), stringResource(Res.string.prays),
        )

        @Composable
        fun dataNotFound(text: String) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = text, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.loved),
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
                        searchWidget { searchValue = it }
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
                            dataNotFound(text = "Nenhuma oração encontrada.")
                        } else {
                            lazyColumn {
                                items(filteredPrays) { pray ->
                                    PrayRow(
                                        navigator,
                                        pray = pray,
                                        loved = pray.id in lovedIdPrays,
                                        onToggleLoved = { id ->
                                            coroutineScope.launch {
                                                if (id in lovedIdPrays) {
                                                    lovedIdPrays -= id
                                                } else {
                                                    lovedIdPrays += id
                                                }
                                                configViewModel.saveConfiguration(
                                                    ConfigEntry.FavoritePrays, lovedIdPrays
                                                )
                                            }
                                        })
                                }
                            }
                        }
                    } else {
                        if (filteredSongs.isEmpty()) {
                            dataNotFound(text = "Nenhum cântico encontrado.")
                        } else {
                            lazyColumn {
                                items(filteredSongs) { song ->
                                    SongRow(
                                        navigator,
                                        modifier = Modifier,
                                        song,
                                        loved = song.id in lovedIdSongs,
                                        onToggleLoved = { id ->
                                            coroutineScope.launch {
                                                if (id in lovedIdSongs) {
                                                    lovedIdSongs -= id
                                                } else {
                                                    lovedIdSongs += id
                                                }

                                                configViewModel.saveConfiguration(
                                                    ConfigEntry.FavoriteSongs, lovedIdSongs
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
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                },
                                selected = selectedTabIndex == index,
                                onClick = {
                                    selectedTabIndex = index
                                },
                                modifier = Modifier
                                    .background(
                                        color = if (selectedTabIndex == index) ColorObject.mainColor else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
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
