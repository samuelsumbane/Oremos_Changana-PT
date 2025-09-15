package com.samuelsumbane.oremoscatolico



import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.russhwolf.settings.Settings


//expect fun createSettings(): Settings
// jvmMain
//actual fun createSettings(): Settings {
//    val prefs = Preferences.userRoot().node("OremosChangana")
//    return PreferencesSettings(prefs)
//}

// androidMain
//actual fun createSettings(): Settings {
//    // E como fica para android
//}



data class AppConfigs(
    val themeColor: Int,
    val secondThemeColor: Int,
    val themeMode: String,
    val locale: String,
    val favoriteSongs: Set<Int>,
    val favoritePrays: Set<Int>,
    val currentOremos: String,
    val fontSize: String
)

@Composable
fun OremosCatolicoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            secondary = Color.DarkGray,
            tertiary = Color.White
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color.Gray,
            tertiary = Color.Black
        )
    }

    /**
     * secondary toggles good color for modes;
     *
     * tertiary toggles black or white color. (black for light-mode and white for dark-mode)
     */

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
