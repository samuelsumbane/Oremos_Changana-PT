package com.samuelsumbane.oremoscatolico.view.morepagesPackage

//import androidx.compose.animation.AnimatedContent
//import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
//import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
//import androidx.compose.animation.core.EaseIn
//import androidx.compose.animation.core.EaseOut
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.togetherWith
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.MaterialTheme.typography
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Tab
//import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
import cafe.adriel.voyager.navigator.Navigator
//import com.samuel.oremoschanganapt.repository.ColorObject
//import com.samuelsumbane.oremoscatolico.R
//import com.samuelsumbane.oremoscatolico.SetIdPreference
//import com.samuelsumbane.oremoscatolico.components.BottomAppBarPrincipal
//import com.samuelsumbane.oremoscatolico.components.LoadingScreen
//import com.samuelsumbane.oremoscatolico.components.PrayRow
//import com.samuelsumbane.oremoscatolico.components.SideBar
//import com.samuelsumbane.oremoscatolico.components.SongRow
//import com.samuelsumbane.oremoscatolico.components.lazyColumn
//import com.samuelsumbane.oremoscatolico.globalComponents.searchContainer
import com.samuelsumbane.oremoscatolico.data.Pray
import com.samuelsumbane.oremoscatolico.db.data.Song
//import com.samuelsumbane.oremoscatolico.db.data.praysData
//import com.samuelsumbane.oremoscatolico.db.data.songsData
//import com.samuelsumbane.oremoscatolico.DataCollection
//import com.samuelsumbane.oremoscatolico.getIdSet
////import com.samuelsumbane.oremoscatolico.repository.ColorObject
//import com.samuelsumbane.oremoscatolico.saveIdSet
//import com.samuelsumbane.oremoscatolico.view.states.AppState.isLoading
//import kotlinx.coroutines.launch

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

    Text("loved data")

//    LaunchedEffect(Unit) {
//        lovedIdSongs = getIdSet(context, SetIdPreference.SONGS_ID.preferenceName)
//
//        lovedIdSongs.forEach { id ->
//            songsData
//                .firstOrNull { it.id == id }
//                ?.let { song -> lSongs.add(song) }
//        }
//
//        lovedIdPrays = getIdSet(context, SetIdPreference.PRAYS_ID.preferenceName)
//        lovedIdPrays.forEach { id ->
//            praysData
//                .firstOrNull { it.id == id }
//                ?.let {  pray -> lPrays.add(pray) }
//        }
//        isLoading = false
//    }
//
//    val filteredPrays = remember(lPrays, searchValue){
//        if (searchValue.isNotEmpty()) {
//            lPrays.filter { it.title.contains(searchValue, ignoreCase = true) }
//        } else lPrays
//    }
//
//    val filteredSongs = remember(lSongs, searchValue){
//        if (searchValue.isNotBlank()) {
//            val numOrNot = isNumber(searchValue)
//            if (numOrNot) {
//                lSongs.filter { it.number == searchValue }
//            } else {
//                lSongs.filter {
//                    it.title.contains(searchValue, ignoreCase = true)
//                }
//            }
//        } else lSongs
//    }
//
//    var selectedTabIndex by remember { mutableIntStateOf(0) }
//    val tabs = listOf(
//        stringResource(R.string.songs), stringResource(R.string.prays),
//    )
//
//    @Composable
//    fun dataNotFound(text: String) {
//        Column(
//            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = text , textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {Text(text = stringResource(R.string.loved), color = MaterialTheme.colorScheme.tertiary)},
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.Transparent
//                ),
//                navigationIcon = {
//                    IconButton(onClick={ navigator.pop() } ){
//                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
//                    }
//                },
//                actions = {
//                    searchContainer(searchInputLabel = stringResource(R.string.search_loved)) { searchValue = it }
//                }
//            )
//        },
//        bottomBar = {
//            if (isPortrait) BottomAppBarPrincipal(navigator, "morepages")
//        }
//    ) { paddingVales ->
//        Column(
//            Modifier
//                .padding(paddingVales)
//                .fillMaxSize()
//        ) {
//            if (isLoading) {
//                LoadingScreen()
//            } else {
//
//                if (!isPortrait) SideBar(navigator, "canticosAgrupados")
//
//                @Composable
//                fun tabContent(dataCollection: DataCollection) {
//                    if (dataCollection == DataCollection.PRAYS) {
//                        if (lPrays.isEmpty()) {
//                            dataNotFound(text = "Nenhuma oração encontrada.")
//                        } else {
//                            lazyColumn {
//                                items(filteredPrays) { pray ->
//                                    PrayRow(
//                                        navigator,
//                                        pray = pray,
//                                        loved = pray.id in lovedIdPrays,
//                                        onToggleLoved = { id ->
//                                            coroutineScope.launch {
//                                                val newSet = lovedIdPrays.toMutableSet().apply {
//                                                    if (contains(id)) remove(id) else add(id)
//                                                }
//                                                saveIdSet(
//                                                    context,
//                                                    newSet,
//                                                    SetIdPreference.PRAYS_ID.preferenceName
//                                                )
//                                                lovedIdPrays = newSet
//                                            }
//                                        })
//                                }
//                            }
//                        }
//                    } else {
//                        if (lSongs.isEmpty()) {
//                            dataNotFound(text = "Nenhum cântico encontrado.")
//                        } else {
//                            lazyColumn {
//                                items(filteredSongs) { song ->
//                                    SongRow(
//                                        navigator,
//                                        song,
//                                        loved = song.id in lovedIdSongs,
//                                        onToggleLoved = { id ->
//                                            coroutineScope.launch {
//                                                val newSet = lovedIdSongs.toMutableSet().apply {
//                                                    if (contains(id)) remove(id) else add(id)
//                                                }
//                                                saveIdSet(
//                                                    context,
//                                                    newSet,
//                                                    SetIdPreference.SONGS_ID.preferenceName
//                                                )
//                                                lovedIdSongs = newSet
//                                            }
//                                        }
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//
//                TabRow(
//                    selectedTabIndex = selectedTabIndex,
//                    modifier = Modifier.padding(10.dp).background(Color.Gray)
//                ) {
//                    tabs.forEachIndexed { index, tab ->
//                        Tab(
//                            text = {
//                                Text(
//                                    text = tab,
//                                    style = typography.bodyMedium,
//                                    fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal,
//                                    color = MaterialTheme.colorScheme.tertiary
//                                )
//                            },
//                            selected = selectedTabIndex == index,
//                            onClick = {
//                                selectedTabIndex = index
//                            },
//                            modifier = Modifier
//                                .background(
//                                    color = if (selectedTabIndex == index) ColorObject.mainColor else Color.Transparent,
//                                    shape = RoundedCornerShape(8.dp)
//                                ),
//                            selectedContentColor = ColorObject.mainColor,
//                        )
//                    }
//                }
//
//                AnimatedContent(
//                    targetState = selectedTabIndex,
//                    transitionSpec = {
//                        slideIntoContainer(
//                            animationSpec = tween(400, easing = EaseIn), towards = Up
//                        ).togetherWith(
//                            slideOutOfContainer(
//                                animationSpec = tween(450, easing = EaseOut), towards = Down
//                            )
//                        )
//                    },
//                ) { selectedTabIndex ->
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.spacedBy(20.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        when (selectedTabIndex) {
//                            0 -> tabContent(DataCollection.SONGS)
//                            1 -> tabContent(DataCollection.PRAYS)
//                        }
//                    }
//                }
//            }
//
//        }
//    }
}
