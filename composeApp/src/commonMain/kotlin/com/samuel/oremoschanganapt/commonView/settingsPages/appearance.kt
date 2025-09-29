package com.samuel.oremoschanganapt.commonView.settingsPages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.createSettings
import com.samuel.oremoschanganapt.globalComponents.appearanceWidgets.RowPreviewColor
import com.samuel.oremoschanganapt.globalComponents.appearanceWidgets.colorSelectBox
import com.samuel.oremoschanganapt.repository.isDesktop
import com.samuel.oremoschanganapt.ui.theme.Blue
import com.samuel.oremoschanganapt.ui.theme.BlueColor
import com.samuel.oremoschanganapt.ui.theme.Green
import com.samuel.oremoschanganapt.ui.theme.Lightblue
import com.samuel.oremoschanganapt.ui.theme.Lightgray
import com.samuel.oremoschanganapt.ui.theme.Orange
import com.samuel.oremoschanganapt.ui.theme.Pink
import com.samuel.oremoschanganapt.ui.theme.Purple
import com.samuel.oremoschanganapt.ui.theme.Red
import com.samuel.oremoschanganapt.ui.theme.OliveGreen
import com.samuel.oremoschanganapt.ui.theme.Tomato
import com.samuel.oremoschanganapt.ui.theme.Turquoise
import com.samuel.oremoschanganapt.viewmodels.ConfigEntry
import com.samuel.oremoschanganapt.viewmodels.ConfigScreenViewModel
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.app_color
import oremoschangana.composeapp.generated.resources.arrow_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

//@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearancePage(navigator: Navigator) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.app_color), color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.pop() }
                    ) {
                        Icon(painterResource(Res.drawable.arrow_back), contentDescription = "Go back")
                    }
                }
            )
        },
    ) { innerValues ->
        val scrollState = rememberScrollState()
//        var color by remember { mutableStateOf(Color.Red) }
//        val context = LocalContext.current
        var themeColor by remember { mutableStateOf(ColorObject.mainColor) }
        val colorList = listOf(
            listOf(Lightgray, Lightblue, Blue, BlueColor),
            listOf(Orange, Tomato, Red, OliveGreen),
            listOf(Pink, Purple, Turquoise, Green)
        )

        val configViewModel = remember { ConfigScreenViewModel(createSettings()) }

        themeColor.let {
            Column(
                modifier = Modifier
                    .padding(innerValues)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    Modifier.fillMaxWidth(if (isDesktop()) 0.5f else 1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(60.dp))

                    RowPreviewColor(themeColor)

                    Spacer(Modifier.height(60.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        for (eachList in colorList) {
                            Row(
                                modifier = Modifier.fillMaxWidth(0.8f),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                for (color in eachList) {
                                    colorSelectBox(color = color, selected = false) {
                                        configViewModel.saveConfiguration(ConfigEntry.ThemeColor, color.toArgb())

                                        ColorObject.mainColor = color
                                        themeColor = color

                                        ColorObject.secondColor = Color.Transparent
                                        configViewModel.saveConfiguration(ConfigEntry.SecondThemeColor, Color.Transparent.toArgb())
                                    }
                                }
                            }
                        }

                    }

                }


            }
        }
//        LoadingScreen()
    }

}
