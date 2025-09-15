package com.samuelsumbane.oremoscatolico.commonView

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Right
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuelsumbane.oremoscatolico.AditionalVerticalScroll
import com.samuelsumbane.oremoscatolico.BottomNav
import com.samuelsumbane.oremoscatolico.createSettings
import com.samuelsumbane.oremoscatolico.db.data.songsData
import com.samuelsumbane.oremoscatolico.globalComponents.AppSideBar
import com.samuelsumbane.oremoscatolico.globalComponents.LoadingScreen
import com.samuelsumbane.oremoscatolico.globalComponents.ScrollToFirstItemBtn
import com.samuelsumbane.oremoscatolico.globalComponents.SongRow
import com.samuelsumbane.oremoscatolico.globalComponents.platformWidth
import com.samuelsumbane.oremoscatolico.globalComponents.textFontSize
import com.samuelsumbane.oremoscatolico.isAndroid
import com.samuelsumbane.oremoscatolico.isDesktop
import com.samuelsumbane.oremoscatolico.repository.PageName
import com.samuelsumbane.oremoscatolico.repository.isNumber
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigEntry
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigScreenViewModel
import kotlinx.coroutines.launch
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.advanced_search
import oremoscatolico.composeapp.generated.resources.arrow_back
import org.jetbrains.compose.resources.painterResource


//@OptIn(ExperimentalMaterial3Api::class)

class SongsScreen(
    val value: String,
    val readbleValue: String
) : Screen {
    @Composable
    override fun Content() {
        CommonSongsPage(value, readbleValue)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonSongsPage(value: String, readbleValue: String) {

    val navigator = LocalNavigator.currentOrThrow
    var searchValue by remember { mutableStateOf("") }
    var advancedSearchString by remember { mutableStateOf("") }
    val allSongs = songsData
    var activeInput by remember { mutableIntStateOf(0) }
    var searchInputActive by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    //    val context = LocalContext.current
    var lovedSongsIds by remember { mutableStateOf(setOf<Int>()) }

    //    LaunchedEffect(Unit) {
    //        lovedSongsIds = getIdSet(context, SetIdPreference.SONGS_ID.preferenceName)
    //    }


    val data = when (value) {
        "todos" -> allSongs
        "new" -> allSongs.filter { it.number.startsWith("0") }
        else -> allSongs.filter { it.group == value }
    }

    LaunchedEffect(activeInput) {
        searchValue = ""
        advancedSearchString = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = readbleValue
                            .replaceFirstChar { e -> e.uppercase() }
                            .replace(" | ", "\n"),
                        fontSize = if ("|" in readbleValue) (textFontSize().value - 1).sp else textFontSize(),
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                },

                navigationIcon = {
                    IconButton(onClick = { navigator.pop() } ){
                        Icon(painterResource(Res.drawable.arrow_back), contentDescription = null)
                    }
                },
                actions = {
                    Row(modifier = Modifier.padding(50.dp, 0.dp, 0.dp, 0.dp)) {

                        AnimatedContent(
                            targetState = activeInput,
                            transitionSpec = {
                                slideIntoContainer(
                                    animationSpec = tween(400, easing = EaseIn),
                                    towards = Left
                                ).togetherWith(
                                    slideOutOfContainer(
                                        animationSpec = tween(450, easing = EaseOut),
                                        towards = Right
                                    )
                                )
                            },
                        ) { activeInput ->
                            when (activeInput) {
//                                0 -> DesktopSearchContainer("Pesquisar cântico") {
//                                    searchValue = it
//                                }
//
//                                1 -> DesktopSearchContainer("Pesquisa avançada") {
//                                    advancedSearchString = it
//                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .padding(0.dp, 7.dp, 0.dp, 0.dp)
                                .width(40.dp)
                        ) {
                            IconButton(
                                modifier = Modifier,
                                onClick = {
                                    activeInput = if (activeInput == 0) 1 else 0
                                    searchInputActive = !searchInputActive
                                    if (searchInputActive) {
                                        //                                        toastAlert(
                                        //                                            context,
                                        //                                            text = "Pesquisa avançada activada\n" +
                                        //                                                    "Encontra o cântico pelo seu conteúdo"
                                        //
                                        //                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.advanced_search),
                                    contentDescription = "Trocar o campo de pesquisa",
                                    tint = if (searchInputActive) ColorObject.mainColor else MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.size(33.dp).padding(top = 6.dp),
                                )
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomNav(navigator, PageName.SONGSGROUP.value)
        }

        ) { paddingVales ->

        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showUpButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }

        val configViewModal = remember { ConfigScreenViewModel(createSettings()) }

        LaunchedEffect(Unit) {
            val defaultConfig = configViewModal.loadConfigurations()
            lovedSongsIds = defaultConfig.favoriteSongs
        }

        when {
            allSongs.isEmpty() -> LoadingScreen()
            else -> {
                val filteredSongs = remember(data, searchValue, advancedSearchString) {
                    if (searchValue.isNotBlank()) {
                        val numOrNot = isNumber(searchValue)
                        if (numOrNot) {
                            data.filter { it.number == searchValue }
                        } else {
                            data.filter {
                                it.title.contains(searchValue, ignoreCase = true)
                            }
                        }
                    } else if (advancedSearchString.isNotBlank()) {
                        val numOrNot = isNumber(advancedSearchString)
                        if (numOrNot) {
                            data.filter { it.number == advancedSearchString }
                        } else {
                            data.filter {
                                it.title.contains(advancedSearchString, ignoreCase = true) ||
                                        it.body.contains(
                                            advancedSearchString,
                                            ignoreCase = true
                                        )
                            }
                        }
                    } else data
                }

                Row(Modifier
//                        .background(Color.Red)
                    .fillMaxSize().padding(paddingVales)) {
                    if (isDesktop()) AppSideBar(navigator, PageName.SONGSGROUP.value)

                    Row(modifier = Modifier
//                            .background(Color.Cyan)
//                        .weight(1f)
                        .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .padding(top = 30.dp, end = 8.dp, bottom = 8.dp, start = 8.dp)
                                .fillMaxSize(0.97f),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(filteredSongs) {
                                SongRow(
                                    navigator,
                                    modifier = Modifier.platformWidth(),
                                    song = it,
                                    loved = it.id in lovedSongsIds,
                                    onToggleLoved = { id ->
                                        coroutineScope.launch {

                                            if (id in lovedSongsIds) {
                                                lovedSongsIds -= id
                                            } else {
                                                lovedSongsIds += id
                                            }

                                            configViewModal.saveConfiguration(
                                                ConfigEntry.FavoriteSongs, lovedSongsIds
                                            )
                                        }
                                    }
                                )
                            }
                        }

                        if (isAndroid()) {
                            if (showUpButton) {
                                ScrollToFirstItemBtn(
                                    modifier = Modifier.align(alignment = Alignment.Bottom)
                                ) {
                                    coroutineScope.launch {
                                        listState.scrollToItem(0)
                                    }
                                }
                            }
                        } else {
                            AditionalVerticalScroll(lazyListState = listState, scrollState = null)
                        }
                    }
                }
                //                ShortcutsButton(navigator)
            }
        }
    }
}