package com.samuel.oremoschanganapt.commonView

//import com.samuel.oremoschanganapt.globalComponents.DataCollection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuel.oremoschanganapt.states.UIState.isFullScreen
//import com.samuel.oremoschanganapt.view.states.UIState.isFullScreen
import com.samuel.oremoschanganapt.BottomNav
import com.samuel.oremoschanganapt.ConfigureReminderScreen
import com.samuel.oremoschanganapt.createSettings
import com.samuel.oremoschanganapt.data.Pray

import com.samuel.oremoschanganapt.data.praysData
import com.samuel.oremoschanganapt.db.data.Song
import com.samuel.oremoschanganapt.db.data.songsData
import com.samuel.oremoschanganapt.globalComponents.LoadingScreen
import com.samuel.oremoschanganapt.globalComponents.StarButton
import com.samuel.oremoschanganapt.globalComponents.PagerContent
import com.samuel.oremoschanganapt.globalComponents.showSnackbar
import com.samuel.oremoschanganapt.repository.isAndroid
import com.samuel.oremoschanganapt.repository.isDesktop
import com.samuel.oremoschanganapt.repository.DataCollection
import com.samuel.oremoschanganapt.repository.PageName
import com.samuel.oremoschanganapt.shareContent
import com.samuel.oremoschanganapt.states.AppState.isLoading
import com.samuel.oremoschanganapt.viewmodels.ConfigEntry
import com.samuel.oremoschanganapt.viewmodels.ConfigScreenViewModel

import kotlinx.coroutines.launch
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.content_copy
import oremoschangana.composeapp.generated.resources.copied_text
import oremoschangana.composeapp.generated.resources.copy
import oremoschangana.composeapp.generated.resources.fullscreen
import oremoschangana.composeapp.generated.resources.fullscreen_exit
import oremoschangana.composeapp.generated.resources.more_vert
import oremoschangana.composeapp.generated.resources.notifications
import oremoschangana.composeapp.generated.resources.options
import oremoschangana.composeapp.generated.resources.outline_arrow_back
import oremoschangana.composeapp.generated.resources.outline_share
import oremoschangana.composeapp.generated.resources.pray
import oremoschangana.composeapp.generated.resources.reminder
import oremoschangana.composeapp.generated.resources.share
import oremoschangana.composeapp.generated.resources.song
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.minus
import kotlin.collections.plus


@OptIn(ExperimentalMaterial3Api::class)

data class EachPageScreen(
    val dataCollection: DataCollection,
    val itemId: Int,
) : Screen {
    @Composable
    override fun Content() {
        val reminder = stringResource(Res.string.reminder)
        val share = stringResource(Res.string.share)
        val fullscreen = stringResource(Res.string.fullscreen)
        val copy = stringResource(Res.string.copy)
        var expanded by remember { mutableStateOf(false) }
        var pageContentId by remember { mutableIntStateOf(0) } //prayOrSongId
        var pageNumber by remember { mutableStateOf("") }
        var pageTitle by remember { mutableStateOf("") }
        var pageSubTitle by remember { mutableStateOf("") }
        var pageBody by remember { mutableStateOf("") }
        val navigator = LocalNavigator.currentOrThrow

        val coroutineScope = rememberCoroutineScope()
        val configViewModal = remember { ConfigScreenViewModel(createSettings()) }

        val btnsIcons = buildMap {
            if (isDesktop()) put(copy, Res.drawable.content_copy)
            if (isAndroid()) {
                put(share, Res.drawable.outline_share)
                put(fullscreen, Res.drawable.fullscreen)
                put(reminder, Res.drawable.notifications)
            }
        }

        val data = if (dataCollection == DataCollection.SONGS) songsData else praysData

//    val scrollState = rememberScrollState()
        val pagerState = rememberPagerState(
            initialPage = itemId - 1,
            pageCount =
                if (dataCollection == DataCollection.SONGS) {
                    { data.size + 1}
                } else {
                    { data.size }
                }
        )

        val clipboardManager = LocalClipboardManager.current

        var lovedIdPrays by remember { mutableStateOf(setOf<Int>()) }
        var lovedIdSongs by remember { mutableStateOf(setOf<Int>()) }

        LaunchedEffect(Unit) {
            val defaultConfig = configViewModal.loadConfigurations()
            if (dataCollection == DataCollection.SONGS)
                lovedIdSongs = defaultConfig.favoriteSongs
            else
                lovedIdPrays = defaultConfig.favoritePrays
        }

        /**
         * In pagerState, initialPage receives songId - 1 because, will be page + 1
         * in page inside HorizontalPager
         */

        val isItemLoved by remember(pageContentId, lovedIdPrays, lovedIdSongs) {
            derivedStateOf {
                if (dataCollection == DataCollection.SONGS) {
                    pageContentId in lovedIdSongs
                } else {
                    pageContentId in lovedIdPrays
                }
            }
        }

        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        val copiedTextMessage = stringResource(Res.string.copied_text)

        @Composable
        fun pager(
            modifier: Modifier,
            showShortcutButton: Boolean = true
        ) {
            HorizontalPager(state = pagerState, pageSpacing = 15.dp) { page ->
                if (data == songsData) {
                    songsData.first { it.id == page + 1 }.run {
                        PagerContent(
                            modifier = modifier,
                            navigator,
                            title = "$number - ${title.uppercase()}",
                            subTitle = subTitle,
                            body = body,
                            showShortcutButton = showShortcutButton
                        )
                        pageNumber = number
                        pageTitle = "$number - ${title.uppercase()}"
                        pageSubTitle = subTitle
                        pageBody = body
                        if (pageContentId != id) pageContentId = id
                    }
                } else {
                    praysData.first { it.id == page + 1 }.run {
                        PagerContent(
                            modifier = modifier,
                            navigator,
                            title = title.uppercase(),
                            subTitle = subTitle,
                            body = body,
                            showShortcutButton = showShortcutButton
                        )
                        pageTitle = title.uppercase()
                        pageSubTitle = subTitle
                        pageBody = body
                        if (pageContentId != id) pageContentId = id
                    }
                    isLoading = false
                }
            }
        }

        val pageContent = if (data == songsData) {
            "$pageNumber - $pageTitle \n\n $pageSubTitle \n\n $pageBody"
        } else {
            "$pageTitle \n\n $pageSubTitle \n\n $pageBody"
        }

        if (isFullScreen) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(
                    onClick = {
                        isFullScreen = false
                        expanded = false
                    },
                    modifier = Modifier
                        .padding(top = 30.dp, end = 5.dp)
                        .align(Alignment.End)
                ) {
                    Icon(
                        painterResource(Res.drawable.fullscreen_exit),
                        contentDescription = "Toggle fullscreen",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
                pager(modifier = Modifier, showShortcutButton = false)
            }
        } else {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = if (dataCollection == DataCollection.SONGS) stringResource(
                                    Res.string.song
                                ) else stringResource(Res.string.pray),
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }, colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ), navigationIcon = {
                            IconButton(onClick = { navigator.pop() }) {

                                Icon(
                                    painter = painterResource(resource = Res.drawable.outline_arrow_back),
                                    //                                contentDescription = stringResource(R.string.go_back)
                                    contentDescription = "go back"
                                )
                            }
                        }, actions = {
                            //                        val context = LocalContext.current
                            // ---------->>
                            StarButton(lovedState = isItemLoved) {
                                coroutineScope.launch {
                                    if (dataCollection == DataCollection.SONGS) {
                                        if (pageContentId in lovedIdSongs) {
                                            lovedIdSongs -= pageContentId
                                        } else {
                                            lovedIdSongs += pageContentId
                                        }

                                        configViewModal.saveConfiguration(
                                            ConfigEntry.FavoriteSongs, lovedIdSongs
                                        )
                                    } else {
                                        if (pageContentId in lovedIdPrays) {
                                            lovedIdPrays -= pageContentId
                                        } else {
                                            lovedIdPrays += pageContentId
                                        }

                                        configViewModal.saveConfiguration(
                                            ConfigEntry.FavoritePrays, lovedIdPrays
                                        )
                                    }
                                }
                            }

                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    painterResource(Res.drawable.more_vert),
                                    contentDescription = stringResource(Res.string.options)
                                )
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    properties = PopupProperties(focusable = true),
                                    modifier = Modifier.shadow(
                                        elevation = 3.dp,
                                        spotColor = Color.DarkGray
                                    )
                                ) {
                                    for ((name, icon) in btnsIcons) {
                                        DropdownMenuItem(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = { Text(name) },
                                            trailingIcon = {
                                                Icon(
                                                    painterResource(icon),
                                                    contentDescription = "l",
                                                    Modifier.size(18.dp)
                                                )
                                            },
                                            onClick = {
                                                when (name) {
                                                    reminder -> {
                                                        navigator.push(
                                                            ConfigureReminderScreen(
                                                                itemId = pageContentId,
                                                                table = if (data == songsData) "Song" else "Pray",
                                                                reminderIdParam = 0L
                                                            )
                                                        )
                                                    }

                                                    share -> shareContent(pageContent)

                                                    fullscreen -> isFullScreen = true

                                                    copy -> {
                                                        clipboardManager.setText(
                                                            AnnotatedString(pageContent)
                                                        )

                                                        showSnackbar(
                                                            scope,
                                                            snackbarHostState,
                                                            message = copiedTextMessage
                                                        )
                                                        expanded = false
                                                    }
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        })
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }
            ) { paddingValues ->
                    pager(modifier = Modifier.padding(paddingValues))
            }
        }
    }
}
