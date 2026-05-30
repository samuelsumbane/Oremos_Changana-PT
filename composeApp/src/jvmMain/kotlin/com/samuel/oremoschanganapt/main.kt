package com.samuel.oremoschanganapt

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import com.samuel.oremoschanganapt.data.jvmpraysList
import com.samuel.oremoschanganapt.db.data.jvmsongsList
import com.samuel.oremoschanganapt.desktopWidgets.DesktopSearchContainer
import com.samuel.oremoschanganapt.desktopWidgets.JVMPagerContent
import com.samuel.oremoschanganapt.ui_core.globalComponents.Pray
import com.samuel.oremoschanganapt.ui_core.globalComponents.Song
import com.samuel.oremoschanganapt.ui_core.ColorObject
import com.samuel.oremoschanganapt.ui_core.Configs.appLocale
import com.samuel.oremoschanganapt.ui_core.states.UIState.configFontSize
import com.samuel.oremoschanganapt.ui_core.states.UIState.themeMode
import com.samuel.oremoschanganapt.view.DesktopHomePage
import com.samuel.oremoschanganapt.presentation.ConfigScreenViewModel
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.icon
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import java.util.Locale
import java.util.prefs.Preferences


fun main() = application {
    startKoin {
        modules(appModule)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Oremos Changana-PT",
        state = rememberWindowState(
            width = 1050.dp,   // largura inicial
            height = 680.dp  // altura inicial
        ),
        icon = painterResource(Res.drawable.icon),
    ) {

//        initKoin()

        val configViewModel = remember { ConfigScreenViewModel(createSettings()) }
        // To keep the density as Android (jetpack compose)

        LaunchedEffect(Unit) {
            val configurations = configViewModel.loadConfigurations()

            appLocale = configurations.locale
            Locale.setDefault(Locale(appLocale))

            configFontSize = configurations.fontSize
            themeMode = configurations.themeMode
            ColorObject.mainColor = Color(configurations.themeColor)
            ColorObject.secondColor =
                if (configurations.secondThemeColor == 0)
                    Color.Transparent
                else
                    Color(configurations.secondThemeColor)
        }


        if (themeMode.isNotBlank()) {
            val appMode = when (themeMode) {
                "Dark" -> true
                "Light" -> false
                else -> isSystemInDarkTheme()
            }

            CompositionLocalProvider(
                LocalDensity provides Density(density = 1f, fontScale = 1f),
            ) {

                oremoschanganaptTheme(darkTheme = appMode) {
//                    Navigator(MorePagesScreen)
//                    Navigator(AppearanceScreen)
//                    Navigator(DesktopSettingsScreen)
//                    Navigator(RemindersScreen)
                    Navigator(HomeScreen())
//                Navigator(CommonAboutAppScreen)
                }
            }
        }
    }
}

actual class ReminderRepository actual constructor() {
    actual fun insert(reminder: Reminder): Long = 0L
    actual fun getAll(): List<Reminder> = emptyList()
    actual fun deleteById(id: Long): Int = 0
    actual fun update(reminder: Reminder): Int = 0
    actual fun close() {
        // Nothing
    }
}

actual class RemindersScreen : Screen {
    @Composable
    override fun Content() {
        /** Nothing happens in desktop */
    }
}

actual class ConfigureReminderScreen actual constructor(
    private val itemId: Int,
    private val table: String,
    private val reminderIdParam: Long
) : Screen {
    @Composable
    override fun Content() {
        /** Nothing happens in desktop */
    }
}


actual class HomeScreen : Screen {
    @Composable
    override fun Content() {
        DesktopHomePage()
    }
}



@Composable
actual fun AditionalVerticalScroll(
    modifier: Modifier,
    lazyListState: LazyListState?,
    scrollState: ScrollState?
) {
    val scrollBarModifier = modifier
//        .background(Color.Gray)
        .fillMaxHeight()
        .width(12.dp)

    val scrollBarStyle = ScrollbarStyle(
        minimalHeight = 80.dp,
        thickness = 12.dp,
        shape = RoundedCornerShape(50),
        hoverDurationMillis = 500,
        unhoverColor = Color.Gray,
        hoverColor = Color.Gray
    )


    if (lazyListState != null) {
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(lazyListState),
            modifier = scrollBarModifier,
            style = scrollBarStyle
        )
    }

    if (scrollState != null) {
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = scrollBarModifier,
            style = scrollBarStyle
        )
    }
}


// jvmMain
actual fun createSettings(): Settings {
    val prefs = Preferences.userRoot().node("OremosChangana")
    return PreferencesSettings(prefs)
}


@Composable
actual fun BottomNav(
    navigator: Navigator,
    activePage: String,
) {
    /**
     * On the desktop, this function will not do anything
     */
}

@Composable
actual fun searchWidget(
    searchInputLabel: String,
    expanded: Boolean,
    onExpand: (Boolean) -> Unit,
    searchValue: (String) -> Unit
) {
    DesktopSearchContainer(searchInputLabel, searchValue)
}


actual fun shareContent(text: String) {
    // Actually nothing happens in Desktop
}

/**
 * On the desktop, this function will do anything
 */
@Composable
actual fun shortcutButtonWidget(navigator: Navigator) { }


/**
 * On desktop, the app will always in portrait
 */
@Composable
actual fun isMobilePortrait() = true

@Composable
actual fun PagerContent(
    modifier: Modifier,
    navigator: Navigator,
    title: String,
    subTitle: String,
    body: String,
    showShortcutButton: Boolean,
) {
    JVMPagerContent(
        modifier = modifier,
        navigator = navigator,
        title = title,
        subTitle = subTitle,
        body = body,
    )
}

actual val songsList: List<Song> = jvmsongsList
actual val praysList: List<Pray> = jvmpraysList