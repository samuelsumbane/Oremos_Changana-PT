package com.samuelsumbane.oremoscatolico

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuelsumbane.oremoscatolico.commonView.AgroupedSongsScreen
import com.samuelsumbane.oremoscatolico.desktopWidgets.DesktopSearchContainer
import com.samuelsumbane.oremoscatolico.repository.Configs.appLocale
import com.samuelsumbane.oremoscatolico.states.UIState.configFontSize
import com.samuelsumbane.oremoscatolico.states.UIState.themeMode
import com.samuelsumbane.oremoscatolico.view.DesktopHomePage
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigScreenViewModel
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.icon
import org.jetbrains.compose.resources.painterResource
import java.util.Locale
import java.util.prefs.Preferences

//import com.samuelsumbane.oremoscatolico.

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Oremos Católico",
        state = rememberWindowState(
            width = 1050.dp,   // largura inicial
            height = 680.dp  // altura inicial
        ),
        icon = painterResource(Res.drawable.icon),
    ) {

        val configViewModel = remember { ConfigScreenViewModel(createSettings()) }
//        var configurations by remember { mutableStateOf(AppConfigs.Default) }
//        var configurations by remember { mutableStateOf<AppConfigs>() }
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

                OremosCatolicoTheme(darkTheme = appMode) {
//                    Navigator(MorePagesScreen)
//                    Navigator(AppearanceScreen)
//                    Navigator(DesktopSettingsScreen)
//                    Navigator(RemindersScreen)
            Navigator(HomeScreen)
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

actual object RemindersScreen : Screen {
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


//actual fun scheduleReminder(
//    title: String,
//    message: String,
//    timestamp: Long
//) {
//    scheduleTaskAt(timestamp) {
//        println("⏰ Lembrete agendado para $timestamp")
//    }
//}


actual object HomeScreen : Screen {
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
    searchValue: (String) -> Unit
) {
    DesktopSearchContainer(searchInputLabel, searchValue)
}


actual fun shareContent(text: String) {
    // Actually nothing happens in Desktop
}


@Composable
actual fun shortcutButtonWidget(navigator: Navigator) {
    /**
     * On the desktop, this function will not do anything
     */
}


/**
 * On desktop, the app will always in portrait
 */
@Composable
actual fun isMobilePortrait() = true