package com.samuelsumbane.oremoscatolico

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import com.samuel.oremoschanganapt.repository.ColorObject
//import com.samuelsumbane.ReminderRepository
import com.samuelsumbane.oremoscatolico.repository.Configs
import com.samuelsumbane.oremoscatolico.repository.Configs.appLocale
import com.samuelsumbane.oremoscatolico.repository.Configs.thememode
import com.samuelsumbane.oremoscatolico.commonView.About
import com.samuelsumbane.oremoscatolico.commonView.RemindersPages.ConfigureReminder
import com.samuelsumbane.oremoscatolico.commonView.RemindersPages.RemindersPage
import com.samuelsumbane.oremoscatolico.commonView.settingsPages.AppearancePage
//import com.samuelsumbane.oremoscatolico.globalComponents.showSnackbar
import com.samuelsumbane.oremoscatolico.repository.ReminderDbHelper
import com.samuelsumbane.oremoscatolico.view.DesktopHomePage
//import com.samuelsumbane.oremoscatolico.view.DesktopPraysScreen
import com.samuelsumbane.oremoscatolico.commonView.CommonSettingsPage
import com.samuelsumbane.oremoscatolico.commonView.CommonSettingsScreen
import com.samuelsumbane.oremoscatolico.commonView.PraysScreen
import com.samuelsumbane.oremoscatolico.desktopWidgets.DesktopSearchContainer
//import com.samuelsumbane.oremoscatolico.view.DesktopSettingsPage
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigScreenViewModel
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.icon
//import oremoscatolico.composeapp.generated.resources.ic_launcher
import org.jetbrains.compose.resources.painterResource
import java.util.Locale
import java.util.prefs.Preferences


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Oremos Católico",
        icon = painterResource(Res.drawable.icon),
        alwaysOnTop = true
    ) {
//        App()

        val repository = ReminderRepository()

        // Inserir um lembrete
        val newReminder = Reminder(
            reminderData = 123,
            reminderTable = "minha_tabela",
            reminderDateTime = System.currentTimeMillis()
        )

//        val id = repository.insert(newReminder)
//        println("Lembrete inserido com ID: $id")

        // Buscar todos os lembretes
//        val allReminders = repository.getAll()
//        println("Total de lembretes: ${allReminders.size}")

        var fontSize by remember { mutableStateOf("") }
        var themeMode by remember { mutableStateOf("") }
        var themeColor by remember { mutableStateOf(Color.Unspecified) }
        var secondThemeColor by remember { mutableStateOf(Color.Unspecified) }
//            val themeColor by getThemeColor(context).collectAsState(initial = Color.Unspecified)
        var initialLanguage by remember { mutableStateOf("") }

        //
        var coroutineScope = rememberCoroutineScope()
        val configViewModel = remember { ConfigScreenViewModel(createSettings()) }
//        var configurations by remember { mutableStateOf(AppConfigs.Default) }
//        var configurations by remember { mutableStateOf<AppConfigs>() }
        // To keep the density as Android (jetpack compose)
        LaunchedEffect(Unit) {
            val configurations = configViewModel.loadConfigurations()
            //

//        updateLocale(context, locale = Locale(if (initialLanguage == "404") "pt" else initialLanguage))
            appLocale = configurations.locale
            Locale.setDefault(Locale(appLocale))

            Configs.fontSize = fontSize
            themeMode = configurations.themeMode
            println("mode $thememode")
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

//                SideEffect { change }

                OremosCatolicoTheme(darkTheme = appMode) {
                    Navigator(PraysScreen)
//                    Navigator(AppearanceScreen)
//                    Navigator(DesktopSettingsScreen)
//                    Navigator(RemindersScreen)
//            Navigator(DesktopPraysScreen)
//                Navigator(CommonAboutAppScreen)
//                Navigator(CommonAboutAppScreen)
                }
            }
        }

        repository.close()
    }
}


actual class ReminderRepository actual constructor() {
    private val dbHelper = ReminderDbHelper("reminders.db")

    actual fun insert(reminder: Reminder): Long = dbHelper.insert(reminder)
    actual fun getAll(): List<Reminder> = dbHelper.getAll()
    actual fun deleteById(id: Long): Int = dbHelper.deleteById(id)
    actual fun update(reminder: Reminder): Int = dbHelper.update(reminder)
    actual fun close() = dbHelper.close()
}

actual object RemindersScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        RemindersPage(navigator)
    }
}

actual class ConfigureReminderScreen actual constructor(
    private val itemId: Int,
    private val table: String,
    private val reminderIdParam: Long
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ConfigureReminder(navigator, itemId, table, reminderIdParam)
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

//@Composable
//actual fun AditionalVerticalScroll(scrollState: AppScrollState) {
//    VerticalScrollbar(
//        adapter = when (scrollState) {
//            AppScrollState.LazyListState -> rememberScrollbarAdapter(scrollState)
//            AppScrollState.ScrollState -> rememberScrollbarAdapter(scrollState)
//        },
//        modifier = Modifier.fillMaxHeight().width(12.dp)
//    )
//}

@Composable
actual fun AditionalVerticalScroll(
    lazyListState: LazyListState?,
    scrollState: ScrollState?
) {
    val scrollBarModifier = Modifier
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
