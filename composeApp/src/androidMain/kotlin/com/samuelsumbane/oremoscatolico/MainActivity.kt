package com.samuelsumbane.oremoscatolico

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuelsumbane.oremoscatolico.states.UIState.configFontSize
import com.samuelsumbane.oremoscatolico.view.RemindersPages.ConfigureReminder
import com.samuelsumbane.oremoscatolico.view.RemindersPages.RemindersPage
import com.samuelsumbane.oremoscatolico.components.AndroidSearchContainer
import com.samuelsumbane.oremoscatolico.components.BottomAppBarPrincipal
import com.samuelsumbane.oremoscatolico.components.ShortcutsButton
import com.samuelsumbane.oremoscatolico.data.praysData
import com.samuelsumbane.oremoscatolico.globalComponents.AppSideBar
import com.samuelsumbane.oremoscatolico.globalComponents.LoadingScreen
import com.samuelsumbane.oremoscatolico.repository.Configs.appLocale
import com.samuelsumbane.oremoscatolico.repository.Configs.thememode
import com.samuelsumbane.oremoscatolico.states.UIState
import com.samuelsumbane.oremoscatolico.states.UIState.themeMode
import com.samuelsumbane.oremoscatolico.view.Home
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigScreenViewModel
import java.util.Locale

//import com.russhwolf.settings.PreferencesSettings


//actual class ScreenFactory actual constructor() {
//    actual fun createMainScreen(): Screen = MainScreen()
////    actual fun createProfileScreen(): Screen = ProfileScreen()
////    actual fun createSettingsScreen(): Screen = SettingsScreen()
//}


class  MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        AndroidSettingsHelper.setCurrentActivity(this)
    }
    override fun onPause() {
        super.onPause()
        AndroidSettingsHelper.setCurrentActivity(null)
    }
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Enable edge-to-edge (optional, but recommended for consistency)
            WindowCompat.setDecorFitsSystemWindows(window, false)

            val context = LocalContext.current
            var fontSize by remember { mutableStateOf("") }
            var themeColor by remember { mutableStateOf(Color.Unspecified) }
            var secondThemeColor by remember { mutableStateOf(Color.Unspecified) }
//            val themeColor by getThemeColor(context).collectAsState(initial = Color.Unspecified)
            var initialLanguage by remember { mutableStateOf("") }

            val configViewModel = remember { ConfigScreenViewModel(createSettings()) }

            LaunchedEffect(Unit) {
                val defaultConfigs = configViewModel.loadConfigurations()
//                fontSize = defaultconfig
                themeMode = defaultConfigs.themeMode
                themeColor = Color(defaultConfigs.themeColor)
                secondThemeColor = if (defaultConfigs.secondThemeColor == 0)
                            Color.Transparent
                        else
                            Color(defaultConfigs.secondThemeColor)
                initialLanguage = defaultConfigs.locale

                updateLocale(context, locale = Locale(if (initialLanguage == "404") "pt" else initialLanguage))
                appLocale = initialLanguage

                configFontSize = defaultConfigs.fontSize
                thememode = themeMode
            }

            val appMode = when (themeMode) {
                "Dark" -> true
                "Light" -> false
                else -> isSystemInDarkTheme()
            }

            if (themeColor != Color.Unspecified || initialLanguage == "404") {
                ColorObject.mainColor = themeColor
                ColorObject.secondColor =
                    if (secondThemeColor == Color.Unspecified || secondThemeColor == Color.Transparent) themeColor else secondThemeColor

                if (praysData.isNotEmpty()) {
                    OremosCatolicoTheme(darkTheme = appMode) {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                val postNotificationPermission = rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)
                                LaunchedEffect(key1 = true) {
                                    if (!postNotificationPermission.status.isGranted) {
                                        postNotificationPermission.launchPermissionRequest()
                                    }
                                }
                            }
                            Navigator(HomeScreen)
                        }
                    }
                }
            } else LoadingScreen()
        }
    }
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


actual object HomeScreen : Screen {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Home(navigator)
    }
}

@Composable
actual fun AditionalVerticalScroll(
    modifier: Modifier,
    lazyListState: LazyListState?,
    scrollState: ScrollState?) {
    // Nothing happens in desktop
}


@SuppressLint("StaticFieldLeak")
object AndroidSettingsHelper {
    private var applicationContext: Context? = null
    private var currentActivity: Activity? = null

    fun setCurrentActivity(activity: Activity?) {
        currentActivity = activity
    }

    fun initialize(context: Context) {
        applicationContext = context.applicationContext
    }

    fun getSettings(): Settings {
        val context = applicationContext ?: throw IllegalStateException(
            "Context not initialized! Call AndroidSettingsHelper.initialize() in Application class"
        )
//        return AndroidPreferencesSettings(context)
        val prefs = context.getSharedPreferences("OremosChangana", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(prefs)
    }

    fun getContext(): Context =
        currentActivity ?: applicationContext
        ?: throw IllegalStateException("Context not initialized")

}
actual fun createSettings(): Settings {
    return AndroidSettingsHelper.getSettings()
}

actual class ReminderRepository actual constructor() {
    private val context = AndroidSettingsHelper.getContext()  // cast explícito, vai lançar ClassCastException se não for Context
    private val dbHelper = ReminderDbHelper(context)

    actual fun insert(reminder: Reminder): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("reminderData", reminder.reminderData)
            put("reminderTable", reminder.reminderTable)
            put("reminderDateTime", reminder.reminderDateTime)
        }
        return db.insert("reminders", null, values)
    }

    actual fun getAll(): List<Reminder> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "reminders",
            arrayOf("id", "reminderData", "reminderTable", "reminderDateTime"),
            null, null, null, null, "reminderDateTime ASC"
        )
        val reminders = mutableListOf<Reminder>()
        with(cursor) {
            while (moveToNext()) {
                val reminder = Reminder(
                    id = getLong(getColumnIndexOrThrow("id")),
                    reminderData = getInt(getColumnIndexOrThrow("reminderData")),
                    reminderTable = getString(getColumnIndexOrThrow("reminderTable")),
                    reminderDateTime = getLong(getColumnIndexOrThrow("reminderDateTime"))
                )
                reminders.add(reminder)
            }
            close()
        }
        return reminders
    }

    actual fun deleteById(id: Long): Int {
        val db = dbHelper.writableDatabase
        return db.delete("reminders", "id = ?", arrayOf(id.toString()))
    }

    actual fun update(reminder: Reminder): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("reminderData", reminder.reminderData)
            put("reminderTable", reminder.reminderTable)
            put("reminderDateTime", reminder.reminderDateTime)
        }
        return db.update("reminders", values, "id = ?", arrayOf(reminder.id.toString()))
    }

    actual fun close() = dbHelper.close()
}

@Composable
actual fun BottomNav(
    navigator: Navigator,
    activePage: String,
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    if (isPortrait) BottomAppBarPrincipal(navigator, activePage, "")
}

@Composable
actual fun searchWidget(
    searchInputLabel: String,
    searchValue: (String) -> Unit
) {
    AndroidSearchContainer(
        searchInputLabel = searchInputLabel,
        searchValue = searchValue
    )
}

actual fun shareContent(text: String) {
    val context = AndroidSettingsHelper.getContext()
    shareText(context, text)
}

@Composable
actual fun shortcutButtonWidget(navigator: Navigator) {
    ShortcutsButton(navigator)
}

@Composable
actual fun isMobilePortrait(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
}
