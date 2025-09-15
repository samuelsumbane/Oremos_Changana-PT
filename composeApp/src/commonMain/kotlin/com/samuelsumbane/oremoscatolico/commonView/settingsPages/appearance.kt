package com.samuelsumbane.oremoscatolico.commonView.settingsPages

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuelsumbane.oremoscatolico.createSettings
import com.samuelsumbane.oremoscatolico.globalComponents.appearanceWidgets.RowPreviewColor
import com.samuelsumbane.oremoscatolico.globalComponents.appearanceWidgets.colorSelectBox
import com.samuelsumbane.oremoscatolico.isDesktop
import com.samuelsumbane.oremoscatolico.ui.theme.Blue
import com.samuelsumbane.oremoscatolico.ui.theme.BlueColor
import com.samuelsumbane.oremoscatolico.ui.theme.Green
import com.samuelsumbane.oremoscatolico.ui.theme.Lightblue
import com.samuelsumbane.oremoscatolico.ui.theme.Lightgray
import com.samuelsumbane.oremoscatolico.ui.theme.Orange
import com.samuelsumbane.oremoscatolico.ui.theme.Pink
import com.samuelsumbane.oremoscatolico.ui.theme.Purple
import com.samuelsumbane.oremoscatolico.ui.theme.Red
import com.samuelsumbane.oremoscatolico.ui.theme.RedButton
import com.samuelsumbane.oremoscatolico.ui.theme.Tomato
import com.samuelsumbane.oremoscatolico.ui.theme.Turquoise
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigEntry
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigScreenViewModel
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.app_color
import oremoscatolico.composeapp.generated.resources.arrow_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

//@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearancePage(navigator: Navigator) {
    val coroutineScope = rememberCoroutineScope()
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
        val itemBgColor = ColorObject.mainColor
        val scrollState = rememberScrollState()
//        var color by remember { mutableStateOf(Color.Red) }
//        val context = LocalContext.current
        var themeColor by remember { mutableStateOf(ColorObject.mainColor) }
        val secondThemeColor by remember { mutableStateOf(ColorObject.secondColor) }
        var color by remember { mutableStateOf<Color>(themeColor) }
        var secondColor by remember { mutableStateOf<Color>(secondThemeColor) }
        var isSolidColorTabSelected by remember { mutableStateOf(false) }

        val colorList = listOf(
            listOf(Lightgray, Lightblue, Blue, BlueColor),
            listOf(Orange, Tomato, Red, RedButton),
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
//                                modifier = Modifier.fillMaxSize(0.8f),
                                modifier = Modifier
                                    .fillMaxWidth(0.8f),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                for (color in eachList) {
                                    colorSelectBox(color = color, selected = false) {
                                        configViewModel.saveConfiguration(ConfigEntry.ThemeColor, color.toArgb())

                                        ColorObject.mainColor = color
                                        themeColor = color

                                        ColorObject.secondColor = Color.Unspecified
                                        configViewModel.saveConfiguration(ConfigEntry.SecondThemeColor, Color.Unspecified.toArgb())
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
