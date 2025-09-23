package com.samuelsumbane.oremoscatolico.commonView

import androidx.compose.runtime.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuelsumbane.oremoscatolico.repository.AppMode
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.view.states.UIState.configFontSize
import com.samuelsumbane.oremoscatolico.repository.Configs
import com.samuelsumbane.oremoscatolico.repository.Configs.appLocale
//import com.samuelsumbane.oremoscatolico.repository.Configs.mode
import com.samuelsumbane.oremoscatolico.AditionalVerticalScroll
import com.samuelsumbane.oremoscatolico.AppearanceScreen
import com.samuelsumbane.oremoscatolico.CommonAboutAppScreen
import com.samuelsumbane.oremoscatolico.createSettings
import com.samuelsumbane.oremoscatolico.globalComponents.AppSideBar
import com.samuelsumbane.oremoscatolico.globalComponents.ConfigColumn
import com.samuelsumbane.oremoscatolico.globalComponents.KeyValueTextRow
import com.samuelsumbane.oremoscatolico.globalComponents.LoadingScreen
import com.samuelsumbane.oremoscatolico.globalComponents.MinCircular
import com.samuelsumbane.oremoscatolico.globalComponents.RadioButtonDialog
import com.samuelsumbane.oremoscatolico.globalComponents.platformWidth
import com.samuelsumbane.oremoscatolico.globalComponents.textFontSize
import com.samuelsumbane.oremoscatolico.repository.isDesktop
import com.samuelsumbane.oremoscatolico.repository.PageName
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigEntry
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigScreenViewModel
import com.samuelsumbane.oremoscatolico.viewmodels.OremosLangsMap
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.app_color
import oremoscatolico.composeapp.generated.resources.appearance
import oremoscatolico.composeapp.generated.resources.arrow_back
import oremoscatolico.composeapp.generated.resources.configurations
import oremoscatolico.composeapp.generated.resources.dark
import oremoscatolico.composeapp.generated.resources.font_size
import oremoscatolico.composeapp.generated.resources.huge
import oremoscatolico.composeapp.generated.resources.language
import oremoscatolico.composeapp.generated.resources.large
import oremoscatolico.composeapp.generated.resources.light
import oremoscatolico.composeapp.generated.resources.mode
import oremoscatolico.composeapp.generated.resources.normal
import oremoscatolico.composeapp.generated.resources.pt
import oremoscatolico.composeapp.generated.resources.small
import oremoscatolico.composeapp.generated.resources.system
import oremoscatolico.composeapp.generated.resources.ts
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.util.Locale
import kotlin.collections.iterator

object CommonSettingsScreen : Screen {
    @Composable
    override fun Content() {
        CommonSettingsPage()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonSettingsPage() {
    val navigator = LocalNavigator.currentOrThrow
    var showModeDialog by remember { mutableStateOf(false) }

    val stringMode = stringResource(Res.string.mode)

    var mode by remember { mutableStateOf("") }
    val lightString = stringResource(Res.string.light)
    val darkString = stringResource(Res.string.dark)
    val systemString = stringResource(Res.string.system)

    val modeOptions = mapOf(
         AppMode.LIGHT.value to lightString,
         AppMode.DARK.value to darkString,
         AppMode.SYSTEM.value to systemString
    )

//
    var selectedModeOption by remember { mutableStateOf(mode) }

    var visibleAppearanceTab by remember { mutableStateOf(false) }
    var showFontSizesDialog by remember { mutableStateOf(false) }
    var selectedFontSizeOption by remember { mutableStateOf(configFontSize) }

    val fontSizeOptions = mapOf(
        "Small" to stringResource(Res.string.small),
        "Normal" to stringResource(Res.string.normal),
        "Large" to stringResource(Res.string.large),
        "Huge" to stringResource(Res.string.huge)
    )

    var expanded by remember { mutableStateOf(false) }
    var oremosMenuExppanded by remember { mutableStateOf(false) }
    var coroutineScope = rememberCoroutineScope()
    var actualOremosVersion by remember { mutableStateOf("") }
//        val context = LocalContext.current

    val localesAndLanguages = mapOf(
        "pt" to stringResource(Res.string.pt),
        "ts" to stringResource(Res.string.ts)
    )

    var appLanguage by remember { mutableStateOf("") }
    appLanguage = localesAndLanguages[appLocale] ?: stringResource(Res.string.pt)

    //
    val configViewModel = remember { ConfigScreenViewModel(createSettings()) }

    LaunchedEffect(Unit) {
        val defaultConfigValues = configViewModel.loadConfigurations()
        actualOremosVersion = OremosLangsMap[defaultConfigValues.currentOremos] ?: ""
        mode = defaultConfigValues.themeMode
        configFontSize = defaultConfigValues.fontSize
    }

    var themeName = when (mode) {
        AppMode.LIGHT.value -> lightString
        AppMode.DARK.value -> darkString
        else -> systemString
    }

    val scrollState = rememberScrollState()
    val pageTitle = stringResource(Res.string.configurations)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = pageTitle) },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() } ) {
                        Icon(painterResource(Res.drawable.arrow_back), contentDescription = "Go back")
                    }
                },
            )
        }
    ) { paddingValues ->
        Row(Modifier
            .padding(paddingValues)
            .padding(end = 5.dp)
//            .background(Color.Blue)
            .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isDesktop()) AppSideBar(navigator, PageName.SETTINGS.value)

            if (mode.isNotBlank()) {
                Row(
                    modifier = Modifier
                        .platformWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .verticalScroll(scrollState)
                            .weight(1f),
//                                    contentAlignment = Alignment.CenterHorizontally
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(50.dp))

                        Column(
                            modifier = Modifier.width(400.dp)
                        ) {

                            ConfigColumn(
                                title = stringResource(Res.string.appearance),
                            ) {
                                KeyValueTextRow(key = stringMode, value = themeName) {
                                    showModeDialog = true
                                }

                                Row (
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                        .height(30.dp)
                                        .clickable { navigator.push(AppearanceScreen) },
                                    Arrangement.SpaceBetween
                                ) {
                                    Text(text = stringResource(Res.string.app_color), fontSize = textFontSize())
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

                            HorizontalDivider(Modifier.height(2.dp))

                            if (showModeDialog) {
                                RadioButtonDialog(
                                    showDialog = showModeDialog,
                                    title = stringMode,
                                    options = modeOptions.values.toList(),
                                    selectedOption = selectedModeOption,
                                    onOptionSelected = { option ->
                                        selectedModeOption = option
                                        showModeDialog = false
                                        configViewModel.saveConfiguration(ConfigEntry.ThemeMode, modeOptions.entries.first { it.value == option }.key)
                                        themeName = option

//                                restartActivity(context)
                                    },
                                    onDismiss = { showModeDialog = false }
                                )
                            }

                            ConfigColumn(title = "Preferências") {

                                if (configFontSize.isNotBlank()) {
                                    KeyValueTextRow(
                                        key = stringResource(Res.string.font_size),
                                        value = fontSizeOptions[configFontSize] ?: "Normal_") {
//                                    value = "Normal" ?: "") {
                                        showFontSizesDialog = true
                                    }
                                } else {
                                    MinCircular()
                                }


                                KeyValueTextRow(key = stringResource(Res.string.language), value = appLanguage) {
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
                                                configViewModel.saveConfiguration(ConfigEntry.Locale, locale)
                                                Locale.setDefault(Locale(locale))
//                                                updateLocale(context, Locale(locale))
                                                expanded = false
//                                    Log.d("applocale", "$locale")
//                                    restartActivity(context)
                                            }
                                        )
                                    }
                                }
//                            }

                                if (showFontSizesDialog) {
                                    RadioButtonDialog(
                                        showDialog = showFontSizesDialog,
                                        title = stringResource(Res.string.font_size),
                                        options = fontSizeOptions.values.toList(),
                                        selectedOption = selectedFontSizeOption,
                                        onOptionSelected = { option ->
                                            selectedFontSizeOption = option

                                            val newFontSizeValue = fontSizeOptions.entries.first { it.value == option }.key

                                            configViewModel.saveConfiguration(ConfigEntry.ConfigFontSize, newFontSizeValue)
                                            configFontSize = newFontSizeValue

                                            showFontSizesDialog = false
                                        },
                                        onDismiss = { showFontSizesDialog = false }
                                    )
                                }
                            }

                            HorizontalDivider(Modifier.height(2.dp))

                            ConfigColumn(title = "Versão do Oremos") {

                                KeyValueTextRow(key = "Versão", value = actualOremosVersion) {
                                    oremosMenuExppanded = true
                                }

                                DropdownMenu(
                                    expanded = oremosMenuExppanded,
                                    onDismissRequest = { oremosMenuExppanded = false },
                                    properties = PopupProperties(focusable = true)
                                ) {
                                    for((langShortName, langFullName) in OremosLangsMap) {
                                        DropdownMenuItem(
                                            text = { Text(text = langFullName) },
                                            onClick = {
                                                configViewModel.saveConfiguration(ConfigEntry.CurrentOremos, langShortName)
                                                oremosMenuExppanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                    if (isDesktop()) AditionalVerticalScroll(
                        modifier = Modifier,
                        lazyListState = null,
                        scrollState = scrollState
                    )
            } else {
//                coroutineScope.launch {
//                    val defaultConfigValues = configViewModel.loadConfigurations()
//                    mode = defaultConfigValues.themeMode
//                }
                LoadingScreen()
            }

        }
    }
}
