package com.samuel.oremoschanganapt.view.sideBar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.R
import com.samuel.oremoschanganapt.components.toastAlert
import com.samuel.oremoschanganapt.globalComponents.CancelButton
import com.samuel.oremoschanganapt.globalComponents.appearanceWidgets.ColorPickerHSV
//import com.samuel.oremoschanganapt.repository.ColorObject
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun newColorPage(navigator: Navigator) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_color), color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "go back")
                    }
                }
            )
        },
    ) { innerValues ->
        val itemBgColor = ColorObject.mainColor
        val scrollState = rememberScrollState()
//        var color by remember { mutableStateOf(Color.Red) }
        val context = LocalContext.current
        val themeColor by remember { mutableStateOf(ColorObject.mainColor) }
        val secondThemeColor by remember { mutableStateOf(ColorObject.secondColor) }
        var color by remember { mutableStateOf<Color>(themeColor) }
        var secondColor by remember { mutableStateOf<Color>(secondThemeColor) }
        var isSolidColorTabSelected by remember { mutableStateOf(false) }


        themeColor.let {
            Column(
                modifier = Modifier.padding(innerValues).fillMaxSize().verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ColorPickerHSV(
                    size = 450,
                    initialColor = it,
                    isSolidColorTabSelected = { isSolidColorTabSelected = it },
                    onColorChanged = { color = it },
                    onSecondColorChanged = { secondColor = it }
                )

                Row(
                    modifier = Modifier.padding(bottom = 20.dp).fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CancelButton(text = stringResource(R.string.cancel)) {
                        navigator.pop()
                    }

                    Button(
                        onClick = {
                            coroutineScope.launch {
//                                if (isSolidColorTabSelected) {
//                                    saveThemeColor(context, color)
//                                    ColorObject.mainColor = color
//                                    /**
//                                     * If user clicked on "Aplicar" button while solid color tab
//                                     * was active is obvious that gradient color is not needed
//                                     */
//                                    ColorObject.secondColor = Color.Transparent
//                                    saveSecondThemeColor(context, Color.Transparent)
//
//                                } else {
//                                    saveThemeColor(context, color)
//                                    ColorObject.mainColor = color
//
//                                    if (secondColor != Color.Transparent) {
//                                        ColorObject.secondColor = secondColor
//                                        saveSecondThemeColor(context, secondColor)
//                                    } else {
//                                        ColorObject.secondColor = Color.Transparent
//                                        saveSecondThemeColor(context, Color.Transparent)
//                                    }
//                                }
                                toastAlert(context, text = "Nova cor foi definida com sucesso.")
                            }
                        }, colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White, containerColor = itemBgColor
                        )
                    ) {
                        Text(text = stringResource(R.string.apply))
                    }
                }
            }
        }
    }
}