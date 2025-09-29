package com.samuel.oremoschanganapt.viewmodels

//import com.samuel.oremoschanganapt.AppSettings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.russhwolf.settings.Settings
import com.samuel.oremoschanganapt.AppConfigs
import com.samuel.oremoschanganapt.repository.FontSizeName
import com.samuel.oremoschanganapt.ui.theme.Green

//import com.samuel.oremoschanganapt.AppConfigs

enum class OremosLangs(val string: String) {
    ChanganaPT("ChanganaPT")
}

val OremosLangsMap = mapOf(
    OremosLangs.ChanganaPT.string to "Changana - Português"
)


sealed class ConfigEntry<T>(
    val key: String,
    val default: T,
    val saver: (Settings, String, T) -> Unit,
    val loader: (Settings, String, T) -> T
) {
    object ThemeColor : ConfigEntry<Int>(
        key = "theme_color",
        default = Green.toArgb(),
        saver = { settings, k, v -> settings.putInt(k, v) },
        loader = { settings, k, d -> settings.getInt(k, d) }
    )

    object SecondThemeColor : ConfigEntry<Int>(
        key = "second_theme_color",
        default = Color.Transparent.toArgb(),
        saver = { settings, k, v -> settings.putInt(k, v) },
        loader = { settings, k, d -> settings.getInt(k, d)}
    )

    object ThemeMode : ConfigEntry<String>(
        key = "theme_mode",
        default = "System",
        saver = { settings, k, v -> settings.putString(k, v) },
        loader = { settings, k, d -> settings.getString(k, d)}
    )

    object Locale : ConfigEntry<String>(
        key = "locale",
        default = "pt",
        saver = { settings, k, v -> settings.putString(k, v) },
        loader = { settings, k, d -> settings.getString(k, d)}
    )

    object FavoriteSongs : ConfigEntry<Set<Int>>(
        key = "favorite_songs",
        default = emptySet(),
        saver = { settings, k, v ->
            // armazena como CSV: "1,2,3"
            settings.putString(k, v.joinToString(","))
        },
        loader = { settings, k, d ->
            val raw = settings.getString(k, "")
            if (raw.isBlank()) d
            else raw
                .split(',')
                .mapNotNull { it.trim().toIntOrNull() }
                .toSet()
        }
    )

    object FavoritePrays : ConfigEntry<Set<Int>>(
        key = "favorite_prays",
        default = emptySet(),
        saver = { settings, k, v ->
            // save like CSV: "1,2,3"
            settings.putString(k, v.joinToString(","))
        },
        loader = { settings, k, d ->
            val raw = settings.getString(k, "")
            if (raw.isBlank()) d
            else raw
                .split(',')
                .mapNotNull { it.trim().toIntOrNull() }
                .toSet()
        }
    )

    object CurrentOremos : ConfigEntry<String>(
        key = "current_oremos",
        default = OremosLangs.ChanganaPT.string,
        saver = { settings, k, v -> settings.putString(k, v) },
        loader = { settings, k, d -> settings.getString(k, d)}
    )

    object ConfigFontSize : ConfigEntry<String>(
        key = "font_scale",
        default = FontSizeName.NORMAL.string,
        saver = { settings, k, v -> settings.putString(k, v) },
        loader = { settings, k, d -> settings.getString(k, d)}
    )
}


class ConfigScreenViewModel(
    private val settings: Settings
) {

    fun loadConfigurations(): AppConfigs {
        return AppConfigs(
            themeColor = ConfigEntry.ThemeColor.loader(settings, ConfigEntry.ThemeColor.key,
                ConfigEntry.ThemeColor.default),
            secondThemeColor = ConfigEntry.SecondThemeColor.loader(settings, ConfigEntry.SecondThemeColor.key,
                ConfigEntry.SecondThemeColor.default),
            themeMode = ConfigEntry.ThemeMode.loader(settings, ConfigEntry.ThemeMode.key,
                ConfigEntry.ThemeMode.default),
            locale = ConfigEntry.Locale.loader(settings, ConfigEntry.Locale.key,
                ConfigEntry.Locale.default),
            favoriteSongs = ConfigEntry.FavoriteSongs.loader(settings, ConfigEntry.FavoriteSongs.key,
                ConfigEntry.FavoriteSongs.default),
            favoritePrays = ConfigEntry.FavoritePrays.loader(settings, ConfigEntry.FavoritePrays.key,
                ConfigEntry.FavoritePrays.default),
            currentOremos = ConfigEntry.CurrentOremos.loader(settings, ConfigEntry.CurrentOremos.key,
                ConfigEntry.CurrentOremos.default),
            fontSize = ConfigEntry.ConfigFontSize.loader(settings, ConfigEntry.ConfigFontSize.key,
                ConfigEntry.ConfigFontSize.default),
        )
    }

    fun <T> saveConfiguration(entry: ConfigEntry<T>, newValue: T) {
        entry.saver(settings, entry.key, newValue)
    }
}
