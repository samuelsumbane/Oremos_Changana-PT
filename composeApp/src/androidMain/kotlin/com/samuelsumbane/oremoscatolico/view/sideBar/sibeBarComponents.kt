package com.samuelsumbane.oremoscatolico.view.sideBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuelsumbane.oremoscatolico.states.UIState.configFontSize
import com.samuelsumbane.oremoscatolico.repository.Configs.appLocale
import com.samuelsumbane.oremoscatolico.repository.Configs.thememode
import com.samuelsumbane.oremoscatolico.R
import com.samuelsumbane.oremoscatolico.createSettings
import com.samuelsumbane.oremoscatolico.globalComponents.DefTabButton
import com.samuelsumbane.oremoscatolico.globalComponents.ExpandContentTabBtn
import com.samuelsumbane.oremoscatolico.globalComponents.KeyValueTextRow
import com.samuelsumbane.oremoscatolico.globalComponents.RadioButtonDialog
import com.samuelsumbane.oremoscatolico.globalComponents.textFontSize
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// AppearanceWidget --------->>
@Composable
fun AppearanceWidget(
    navigator: Navigator,
    modeSetting: String,
) {
    var visibleAppearanceTab by remember { mutableStateOf(false) }
    var mode by remember { mutableStateOf(modeSetting) }
    var showModeDialog by remember { mutableStateOf(false) }
    val lightString = stringResource(R.string.light)
    val darkString = stringResource(R.string.dark)
    val systemString = stringResource(R.string.system)

    val modeOptions = mapOf(
        lightString to "Light",
        darkString to "Dark",
        systemString to "System"
    )
    val themeName = when(thememode) {
        "Light" -> lightString
        "Dark" -> darkString
        else -> systemString
    }

    val stringMode = stringResource(R.string.mode)
    var selectedModeOption by remember { mutableStateOf(mode) }
    var expanded by remember { mutableStateOf(false) }
    var coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val configViewMode = remember { ConfigScreenViewModel(createSettings()) }

//    Launched

    DefTabButton {
        ExpandContentTabBtn(
            painterResource(R.drawable.grid_view_24),
            title = stringResource(R.string.appearance)
        ) { visibleAppearanceTab = !visibleAppearanceTab }

        AnimatedVisibility(visibleAppearanceTab){
            Column {
                Spacer(modifier = Modifier.height(10.dp))

                Column(Modifier.fillMaxWidth()) {
                    KeyValueTextRow(key = stringMode, value = themeName) {
                        showModeDialog = true
                    }

                    Row (
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                            .height(30.dp)
                            .clickable {
//                                navigator.navigate("appearancePage")
                                       },
                        Arrangement.SpaceBetween
                    ) {
                        Text(text = stringResource(R.string.app_color), fontSize = textFontSize())
                        Row(
                            Modifier
                                .size(24.dp)
                                .background(
                                    color = ColorObject.mainColor,
                                    shape = CircleShape
                                )
                        ) {}
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (showModeDialog) {
                RadioButtonDialog(
                    showDialog = showModeDialog,
                    title = stringMode,
                    options = modeOptions.keys.toList(),
                    selectedOption = selectedModeOption,
                    onOptionSelected = { option ->
                        selectedModeOption = option
                        showModeDialog = false
                        CoroutineScope(Dispatchers.IO).launch {
                            modeOptions[option]?.let {
//                                saveThemeMode(context, it)
//                                configViewMode.saveConfiguration(ConfigEntry.)
                                println("new mode is $it")
                            }
                        }
//                        restartActivity(context)
                    },
                    onDismiss = { showModeDialog = false }
                )
            }
        }
    }
}
@Composable
fun PreferencesWidget(navigator: Navigator) {

    var visibleAppearanceTab by remember { mutableStateOf(false) }
    var showFontSizesDialog by remember { mutableStateOf(false) }
    var selectedFontSizeOption by remember { mutableStateOf(configFontSize) }

    val fontSizeOptions = mapOf(
        "Small" to stringResource(R.string.small),
        "Normal" to stringResource(R.string.normal),
        "Large" to stringResource(R.string.large),
        "Huge" to stringResource(R.string.huge)
    )

    var expanded by remember { mutableStateOf(false) }
    var coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val localesAndLanguages = mapOf(
        "pt" to stringResource(R.string.pt),
        "ts" to stringResource(R.string.ts)
    )

    var appLanguage by remember { mutableStateOf("") }
    appLanguage = localesAndLanguages[appLocale] ?: stringResource(R.string.pt)


    DefTabButton {
        ExpandContentTabBtn(
            painterResource(R.drawable.preferences),
            title = "Preferências"
        ) { visibleAppearanceTab = !visibleAppearanceTab }

        AnimatedVisibility(visibleAppearanceTab){
            Column {
                Spacer(modifier = Modifier.height(10.dp))

                Column(Modifier.fillMaxWidth()) {

                    KeyValueTextRow(
                        key = stringResource(R.string.font_size),
                        value = fontSizeOptions[configFontSize] ?: "") {
                        showFontSizesDialog = true
                    }

                    KeyValueTextRow(key = stringResource(R.string.language), value = appLanguage) {
                        expanded = true
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        properties = PopupProperties(focusable = true)
                    ) {

                        for((locale, language) in localesAndLanguages) {
                            DropdownMenuItem(
                                text = { Text(text = language) },
                                onClick = {
//                                    coroutineScope.launch { saveLanguage(context, locale) }
//                                    updateLocale(context, Locale(locale))
                                    expanded = false
//                                    Log.d("applocale", "$locale")
//                                    restartActivity(context)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (showFontSizesDialog) {
                RadioButtonDialog(
                    showDialog = showFontSizesDialog,
                    title = stringResource(R.string.font_size),
                    options = fontSizeOptions.values.toList(),
                    selectedOption = selectedFontSizeOption,
                    onOptionSelected = { option ->
                        selectedFontSizeOption = option
                        CoroutineScope(Dispatchers.IO).launch {
                            val currentMapOption = fontSizeOptions.entries.first { it.value == option }
//                            saveFontSize(context, currentMapOption.key)
//                            configFontSize = currentMapOption.key
                        }
                        showFontSizesDialog = false
                    },
                    onDismiss = { showFontSizesDialog = false }
                )
            }
        }
    }
}


@Composable
fun SidebarText(text: String, bold: Boolean = false, fontSize: Int = 16){
    Text(text = text, fontSize = fontSize.sp,
        fontWeight = if(bold) FontWeight.Bold else FontWeight.Normal,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
fun RowAbout (navigator: Navigator) {
//    DefTabButton {
//        ExpandContentTabBtn(
//            painterResource(Res.drawable.info), "Sobre"
//        ) { navigator.navigate("about") }
//    }
}
