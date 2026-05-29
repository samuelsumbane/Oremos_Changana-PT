package com.samuel.oremoschanganapt.ui_core

//import com.samuel.oremoschanganapt.globalComponents.DataCollection

//import com.samuel.oremoschanganapt.view.states.UIState.isFullScreen

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.samuel.oremoschanganapt.ConfigureReminderScreen
import com.samuel.oremoschanganapt.HomeScreen
import com.samuel.oremoschanganapt.PagerContent
import com.samuel.oremoschanganapt.createSettings
import com.samuel.oremoschanganapt.ui_core.globalComponents.HeartButton
import com.samuel.oremoschanganapt.ui_core.globalComponents.showSnackbar
import com.samuel.oremoschanganapt.praysList
import com.samuel.oremoschanganapt.domain.DataCollection
import com.samuel.oremoschanganapt.domain.isAndroid
import com.samuel.oremoschanganapt.domain.isDesktop
import com.samuel.oremoschanganapt.presentation.CommonPage.CommonPageViewModel
import com.samuel.oremoschanganapt.shareContent
import com.samuel.oremoschanganapt.songsList
import com.samuel.oremoschanganapt.ui_core.states.UIState.isFullScreen
import com.samuel.oremoschanganapt.presentation.ConfigScreenViewModel
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
import org.koin.compose.viewmodel.koinViewModel
import kotlin.collections.iterator


@OptIn(ExperimentalMaterial3Api::class)

data class EachPageScreen(
    val dataCollection: DataCollection,
    val itemId: Int,
    val goToHomeOnBack: Boolean = false
) : Screen {
    @Composable
    override fun Content() {
        EachPage(dataCollection, itemId, goToHomeOnBack)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachPage(
    dataCollection: DataCollection,
    itemId: Int,
    goToHomeOnBack: Boolean = false
) {
    val commonPageViewModel = koinViewModel<CommonPageViewModel>()
    val commonPageUiState by commonPageViewModel.commonPageUiState.collectAsState()

    val reminder = stringResource(Res.string.reminder)
    val share = stringResource(Res.string.share)
    val fullscreen = stringResource(Res.string.fullscreen)
    val copy = stringResource(Res.string.copy)
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

        val data = if (dataCollection == DataCollection.SONGS) songsList else praysList

//    val scrollState = rememberScrollState()
        val pagerState = rememberPagerState(
            initialPage = itemId - 1,
            pageCount = { data.size }
        )

        val clipboardManager = LocalClipboardManager.current

        LaunchedEffect(Unit) {
            val defaultConfig = configViewModal.loadConfigurations()
            if (dataCollection == DataCollection.SONGS)
                commonPageViewModel.fillCommonPageForm(lovedIdSongs = defaultConfig.favoriteSongs)
            else
                commonPageViewModel.fillCommonPageForm(lovedIdPrays = defaultConfig.favoritePrays)
        }


        /**
         * In pagerState, initialPage receives songId - 1 because, will be page + 1
         * in page inside HorizontalPager
         */
        val isItemLoved by remember(
            commonPageUiState.pageContentId,
            commonPageUiState.lovedIdPrays,
            commonPageUiState.lovedIdSongs
        ) {
            derivedStateOf {
                if (dataCollection == DataCollection.SONGS) {
                    commonPageUiState.pageContentId in commonPageUiState.lovedIdSongs
                } else {
                    commonPageUiState.pageContentId in commonPageUiState.lovedIdPrays
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
                if (data == songsList) {
                    val item = songsList[page]
                    PagerContent(
                        modifier = modifier,
                        navigator = navigator,
                        title = "${item.number} - ${item.title.uppercase()}",
                        subTitle = item.subTitle,
                        body = item.body,
                        showShortcutButton = showShortcutButton,
                    )
                    commonPageViewModel.fillCommonPageForm(
                        pageTitle = "${item.number} - ${ item.title.uppercase() }",
                        pageSubTitle = item.subTitle,
                        pageBody = item.body
                    )
                    if (commonPageUiState.pageContentId != item.id) commonPageViewModel.fillCommonPageForm(pageContentId = item.id)
                } else {
                    val item = praysList[page]
                    PagerContent(
                        modifier = modifier,
                        navigator = navigator,
                        title = item.title.uppercase(),
                        subTitle = item.subTitle,
                        body = item.body,
                        showShortcutButton = showShortcutButton,
                    )
                    commonPageViewModel.fillCommonPageForm(
                        pageTitle = item.title.uppercase(),
                        pageSubTitle = item.subTitle,
                        pageBody = item.body
                    )
                    if (commonPageUiState.pageContentId != item.id) commonPageViewModel.fillCommonPageForm(pageContentId = item.id)
                }
            }
        }

        val pageContent = "${commonPageUiState.pageTitle} \n\n ${commonPageUiState.pageSubTitle} \n\n ${commonPageUiState.pageBody.cleanTextFormatting()}"

        if (isFullScreen) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(
                    onClick = {
                        isFullScreen = false
                        commonPageViewModel.fillCommonPageForm(expanded = false)
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
                            IconButton(
                                onClick = {
                                    if (goToHomeOnBack) navigator.push(HomeScreen())
                                    else
                                        navigator.pop()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(resource = Res.drawable.outline_arrow_back),
                                    contentDescription = "go back"
                                )
                            }
                        }, actions = {
                            HeartButton(lovedState = isItemLoved) {
                                coroutineScope.launch {
                                  if (dataCollection == DataCollection.SONGS) {
                                      commonPageViewModel.modifyLovedIdSongs(
                                          commonPageUiState.pageContentId,
                                          configViewModal
                                      )
                                  } else {
                                      commonPageViewModel.modifyLovedIdPrays(
                                          commonPageUiState.pageContentId,
                                          configViewModal
                                      )
                                  }
                                }
                            }

                            IconButton(onClick = { commonPageViewModel.fillCommonPageForm(expanded = !commonPageUiState.expanded)}) {
                                Icon(
                                    painterResource(Res.drawable.more_vert),
                                    contentDescription = stringResource(Res.string.options)
                                )
                                DropdownMenu(
                                    expanded = commonPageUiState.expanded,
                                    onDismissRequest = { commonPageViewModel.fillCommonPageForm(expanded = false) },
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
                                                                itemId = commonPageUiState.pageContentId,
                                                                table = if (data == songsList) "Song" else "Pray",
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
                                                        commonPageViewModel.fillCommonPageForm(expanded = false)
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


fun String.cleanTextFormatting(): String {
    return this
        .replace("<br>", "\n")
        .replace("<i>", "")
        .replace("</i>", "")
        .replace("<b>", "")
        .replace("</b>", "")
        .replace("<small>", "")
        .replace("</small>", "")
}