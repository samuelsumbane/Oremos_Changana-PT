package com.samuel.oremoschanganapt.commonView

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Right
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.AditionalVerticalScroll
import com.samuel.oremoschanganapt.BottomNav
import com.samuel.oremoschanganapt.createSettings
import com.samuel.oremoschanganapt.globalComponents.AppSideBar
import com.samuel.oremoschanganapt.globalComponents.LoadingScreen
import com.samuel.oremoschanganapt.globalComponents.ScrollToFirstItemBtn
import com.samuel.oremoschanganapt.globalComponents.SongRow
import com.samuel.oremoschanganapt.globalComponents.platformWidth
import com.samuel.oremoschanganapt.globalComponents.showSnackbar
import com.samuel.oremoschanganapt.globalComponents.textFontSize
import com.samuel.oremoschanganapt.repository.isDesktop
import com.samuel.oremoschanganapt.repository.PageName
import com.samuel.oremoschanganapt.repository.isNumber
import com.samuel.oremoschanganapt.searchWidget
import com.samuel.oremoschanganapt.shortcutButtonWidget
import com.samuel.oremoschanganapt.songsList
import com.samuel.oremoschanganapt.viewmodels.ConfigEntry
import com.samuel.oremoschanganapt.viewmodels.ConfigScreenViewModel
import kotlinx.coroutines.launch
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.advanced_search
import oremoschangana.composeapp.generated.resources.arrow_back
import org.jetbrains.compose.resources.painterResource


//@OptIn(ExperimentalMaterial3Api::class)

class SongsScreen(
    val value: String,
    val readbleValue: String
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        CommonSongsPage(navigator, value, readbleValue)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonSongsPage(navigator: Navigator, value: String, readbleValue: String) {
    var searchValue by remember { mutableStateOf("") }
    var advancedSearchString by remember { mutableStateOf("") }
    var activeInput by remember { mutableIntStateOf(0) }
    var searchInputActive by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()
    //    val context = LocalContext.current
    var lovedSongsIds by remember { mutableStateOf(setOf<Int>()) }



    val data = when (value) {
        "todos" -> songsList
        "new" -> songsList.filter { it.number.startsWith("0") }
        else -> songsList.filter { it.group == value }
    }

    LaunchedEffect(activeInput) {
        searchValue = ""
        advancedSearchString = ""
    }

    Row {
        AppSideBar(navigator, PageName.SONGSGROUP.value)

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
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                painterResource(Res.drawable.arrow_back),
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        Row(
                            modifier =
                                Modifier
                                    .padding(50.dp, 10.dp, 0.dp, 0.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

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
                                    0 -> searchWidget("Pesquisar cântico") { searchValue = it }
                                    1 -> searchWidget("Pesquisa avançada") {
                                        advancedSearchString = it
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier.width(40.dp)
                            ) {
                                IconButton(
                                    modifier = Modifier,
                                    onClick = {
                                        activeInput = if (activeInput == 0) 1 else 0
                                        searchInputActive = !searchInputActive
                                        if (searchInputActive) {
                                            showSnackbar(
                                                coroutine,
                                                snackbarHostState,
                                                message = "Pesquisa avançada activada\nEncontra o cântico pelo seu conteúdo"
                                            )
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
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
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
                songsList.isEmpty() -> LoadingScreen()
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

                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(paddingVales)
                    ) {

                        Row(
                            modifier = Modifier
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
                                items(filteredSongs, key = { item -> item.id }) {
                                    SongRow(
                                        navigator,
                                        modifier = Modifier.platformWidth(1f),
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

                            if (isDesktop()) {
                                AditionalVerticalScroll(
                                    modifier = Modifier,
                                    lazyListState = listState,
                                    scrollState = null
                                )
                            }
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