package com.samuelsumbane.oremoscatolico.commonView

//import com.samuelsumbane.oremoscatolico.repository.ColorObject
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
import com.samuelsumbane.oremoscatolico.createSettings
import com.samuelsumbane.oremoscatolico.data.Pray
import com.samuelsumbane.oremoscatolico.db.data.Song
import com.samuelsumbane.oremoscatolico.data.praysData
import com.samuelsumbane.oremoscatolico.db.data.songsData
import com.samuelsumbane.oremoscatolico.globalComponents.LoadingScreen
import com.samuelsumbane.oremoscatolico.globalComponents.PrayRow
import com.samuelsumbane.oremoscatolico.globalComponents.SongRow
import com.samuelsumbane.oremoscatolico.globalComponents.lazyColumn
import com.samuelsumbane.oremoscatolico.isDesktop
import com.samuelsumbane.oremoscatolico.repository.DataCollection
import com.samuelsumbane.oremoscatolico.repository.isNumber
import com.samuelsumbane.oremoscatolico.states.AppState.isLoading
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigEntry
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigScreenViewModel
import kotlinx.coroutines.launch
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.arrow_back
import oremoscatolico.composeapp.generated.resources.loved
import oremoscatolico.composeapp.generated.resources.prays
import oremoscatolico.composeapp.generated.resources.songs
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
//    val configuration = LocalConfiguration.current
//    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    var lPrays by remember { mutableStateOf(mutableListOf<Pray>()) }
    val lSongs by remember { mutableStateOf(mutableListOf<Song>()) }

//    val context = LocalContext.current
    var lovedIdPrays by remember { mutableStateOf(setOf<Int>()) }
    var lovedIdSongs by remember { mutableStateOf(setOf<Int>()) }
    val coroutineScope = rememberCoroutineScope()
    val configViewModel = remember { ConfigScreenViewModel(createSettings()) }

    LaunchedEffect(Unit) {
        val defaultConfigurations = configViewModel.loadConfigurations()
        lovedIdSongs = defaultConfigurations.favoriteSongs

        lovedIdSongs.forEach { id ->
            songsData
                .firstOrNull { it.id == id }
                ?.let { song -> lSongs.add(song) }
        }

        lovedIdPrays = defaultConfigurations.favoritePrays

        lovedIdPrays.forEach { id ->
            praysData
                .firstOrNull { it.id == id }
                ?.let {  pray -> lPrays.add(pray) }
        }
        isLoading = false
    }

    val filteredPrays = remember(lPrays, searchValue){
        if (searchValue.isNotEmpty()) {
            lPrays.filter { it.title.contains(searchValue, ignoreCase = true) }
        } else lPrays
    }

    val filteredSongs = remember(lSongs, searchValue){
        if (searchValue.isNotBlank()) {
            val numOrNot = isNumber(searchValue)
            if (numOrNot) {
                lSongs.filter { it.number == searchValue }
            } else {
                lSongs.filter {
                    it.title.contains(searchValue, ignoreCase = true)
                }
            }
        } else lSongs
    }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(Res.string.songs), stringResource(Res.string.prays),
    )

    @Composable
    fun dataNotFound(text: String) {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text , textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text = stringResource(Res.string.loved), color = MaterialTheme.colorScheme.tertiary)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() } ){
                        Icon(painterResource(Res.drawable.arrow_back), contentDescription = "Go back")
                    }
                },
                actions = {
//                    DesktopSearchContainer {
//                        searchValue = it
//                    }
                }
            )
        },
//        bottomBar = {
//            if (isPortrait) BottomAppBarPrincipal(navigator, "morepages")
//        }
    ) { paddingVales ->
        Column(
            Modifier
                .padding(paddingVales)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                LoadingScreen()
            } else {
//                if (!isPortrait) SideBar(navigator, "canticosAgrupados")

                val configViewModal = remember { ConfigScreenViewModel(createSettings()) }

                LaunchedEffect(Unit) {
                    val defaultConfig = configViewModal.loadConfigurations()
                    lovedIdPrays = defaultConfig.favoritePrays
                    lovedIdSongs = defaultConfig.favoriteSongs
                }


                @Composable
                fun tabContent(dataCollection: DataCollection) {
                    if (dataCollection == DataCollection.PRAYS) {
                        if (lPrays.isEmpty()) {
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
                                                configViewModal.saveConfiguration(
                                                    ConfigEntry.FavoritePrays, lovedIdPrays
                                                )
                                            }
                                        })
                                }
                            }
                        }
                    } else {
                        if (lSongs.isEmpty()) {
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

                                                configViewModal.saveConfiguration(
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
                    .fillMaxWidth(if (isDesktop()) 0.5f else 1f )
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
