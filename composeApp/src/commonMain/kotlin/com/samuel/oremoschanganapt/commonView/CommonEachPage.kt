package com.samuel.oremoschanganapt.commonView

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
import com.samuel.oremoschanganapt.ConfigureReminderScreen
import com.samuel.oremoschanganapt.HomeScreen
import com.samuel.oremoschanganapt.PagerContent
import com.samuel.oremoschanganapt.createSettings
import com.samuel.oremoschanganapt.globalComponents.StarButton
import com.samuel.oremoschanganapt.globalComponents.showSnackbar
import com.samuel.oremoschanganapt.praysList
import com.samuel.oremoschanganapt.repository.DataCollection
import com.samuel.oremoschanganapt.repository.isAndroid
import com.samuel.oremoschanganapt.repository.isDesktop
import com.samuel.oremoschanganapt.shareContent
import com.samuel.oremoschanganapt.songsList
import com.samuel.oremoschanganapt.states.UIState.isFullScreen
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


@OptIn(ExperimentalMaterial3Api::class)

data class EachPageScreen(
    val dataCollection: DataCollection,
    val itemId: Int,
    val goToHomeOnBack: Boolean = false
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

        val data = if (dataCollection == DataCollection.SONGS) songsList else praysList

//    val scrollState = rememberScrollState()
        val pagerState = rememberPagerState(
            initialPage = itemId - 1,
            pageCount = { data.size }
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

            println("tamanho: ${songsList.size}")
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
                    pageTitle = "${item.number} - ${item.title.uppercase()}"
                    pageSubTitle = item.subTitle
                    pageBody = item.body
                    if (pageContentId != item.id) pageContentId = item.id
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
                    pageTitle = item.title.uppercase()
                    pageSubTitle = item.subTitle
                    pageBody = item.body
                    if (pageContentId != item.id) pageContentId = item.id
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

        val pageContent = "$pageTitle \n\n $pageSubTitle \n\n ${pageBody.cleanTextFormatting()}"


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
                            IconButton(
                                onClick = {
                                    if (goToHomeOnBack) navigator.push(HomeScreen)
                                    else
                                        navigator.pop()
                                }
                            ) {
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



//
//Exception java.lang.ArrayIndexOutOfBoundsException:
//at java.util.Arrays$ArrayList.get (Arrays.java:4599)
//at com.samuel.oremoschanganapt.commonView.EachPageScreen$Content$pager$1.invoke (CommonEachPage.kt:167)
//at com.samuel.oremoschanganapt.commonView.EachPageScreen$Content$pager$1.invoke (CommonEachPage.kt:165)
//at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke (ComposableLambda.kt:142)
//at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke (ComposableLambda.kt:51)
//at androidx.compose.foundation.pager.PagerLazyLayoutItemProvider$Item$1.invoke (LazyLayoutPager.kt:214)
//at androidx.compose.foundation.pager.PagerLazyLayoutItemProvider$Item$1.invoke (LazyLayoutPager.kt:212)
//at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke (ComposableLambda.kt:121)
//at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke (ComposableLambda.kt:51)
//at androidx.compose.runtime.CompositionLocalKt.CompositionLocalProvider (CompositionLocal.kt:384)
//at androidx.compose.foundation.lazy.layout.LazyLayoutPinnableItemKt.LazyLayoutPinnableItem (LazyLayoutPinnableItem.kt:56)
//at androidx.compose.foundation.pager.PagerLazyLayoutItemProvider.Item (LazyLayoutPager.kt:212)
//at androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactoryKt$SkippableItem$1.invoke (LazyLayoutItemContentFactory.kt:130)
//at androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactoryKt$SkippableItem$1.invoke (LazyLayoutItemContentFactory.kt:129)
//at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke (ComposableLambda.kt:121)
//at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke (ComposableLambda.kt:51)
//at androidx.compose.runtime.CompositionLocalKt.CompositionLocalProvider (CompositionLocal.kt:384)
//at androidx.compose.runtime.saveable.SaveableStateHolderImpl.SaveableStateProvider (SaveableStateHolder.kt:79)
//at androidx.compose.foundation.lazy.layout.LazySaveableStateHolder.SaveableStateProvider (LazySaveableStateHolder.kt:76)
//at androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactoryKt.SkippableItem-JVlU9Rs (LazyLayoutItemContentFactory.kt:129)
//at androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactoryKt.access$SkippableItem-JVlU9Rs (LazyLayoutItemContentFactory.kt:1)
//at androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1.invoke (LazyLayoutItemContentFactory.kt:97)
//at androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1.invoke (LazyLayoutItemContentFactory.kt:87)
//at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke (ComposableLambda.kt:121)
//at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke (ComposableLambda.kt:51)
//at androidx.compose.ui.layout.LayoutNodeSubcompositionsState$subcompose$3$1$1.invoke (SubcomposeLayout.kt:1042)
//at androidx.compose.ui.layout.LayoutNodeSubcompositionsState$subcompose$3$1$1.invoke (SubcomposeLayout.kt:523)
//at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke (ComposableLambda.kt:121)
//at androidx.compose.runtime.internal.ComposableLambdaImpl.invoke (ComposableLambda.kt:51)
//at androidx.compose.runtime.internal.Utils_jvmKt.invokeComposable (Utils.jvm.kt:27)
//at androidx.compose.runtime.ComposerImpl.doCompose-aFTiNEg (Composer.kt:3694)
//at androidx.compose.runtime.ComposerImpl.composeContent--ZbOJvo$runtime_release (Composer.kt:3616)
//at androidx.compose.runtime.CompositionImpl.composeContent (Composition.kt:792)
//at androidx.compose.runtime.Recomposer.composeInitial$runtime_release (Recomposer.kt:1132)
//at androidx.compose.runtime.ComposerImpl$CompositionContextImpl.composeInitial$runtime_release (Composer.kt:4034)
//at androidx.compose.runtime.ComposerImpl$CompositionContextImpl.composeInitial$runtime_release (Composer.kt:4034)
//at androidx.compose.runtime.CompositionImpl.composeInitial (Composition.kt:677)
//at androidx.compose.runtime.CompositionImpl.setContent (Composition.kt:616)
//at androidx.compose.ui.layout.LayoutNodeSubcompositionsState.subcomposeInto (SubcomposeLayout.kt:544)
//at androidx.compose.ui.layout.LayoutNodeSubcompositionsState.subcompose (SubcomposeLayout.kt:514)
//at androidx.compose.ui.layout.LayoutNodeSubcompositionsState.subcompose (SubcomposeLayout.kt:504)
//
