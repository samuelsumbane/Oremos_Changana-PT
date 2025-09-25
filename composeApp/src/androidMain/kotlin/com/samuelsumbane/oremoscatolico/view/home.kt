package com.samuelsumbane.oremoscatolico.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuelsumbane.oremoscatolico.CommonAboutAppScreen
import com.samuelsumbane.oremoscatolico.R
import com.samuelsumbane.oremoscatolico.components.*
import com.samuelsumbane.oremoscatolico.createSettings
//import com.samuelsumbane.oremoscatolico.db.CommonViewModel
import com.samuelsumbane.oremoscatolico.db.data.songsData
import com.samuelsumbane.oremoscatolico.view.sideBar.AppearanceWidget
//import com.samuelsumbane.oremoscatolico.view.sideBar.RowBackup
import com.samuelsumbane.oremoscatolico.view.sideBar.RowAbout
import com.samuelsumbane.oremoscatolico.data.praysData
import com.samuelsumbane.oremoscatolico.globalComponents.HomeTexts
import com.samuelsumbane.oremoscatolico.globalComponents.InputSearch
import com.samuelsumbane.oremoscatolico.globalComponents.PrayRow
import com.samuelsumbane.oremoscatolico.globalComponents.AppSideBar
import com.samuelsumbane.oremoscatolico.globalComponents.AppTitleWidget
import com.samuelsumbane.oremoscatolico.globalComponents.HomeItems
import com.samuelsumbane.oremoscatolico.globalComponents.SongRow
import com.samuelsumbane.oremoscatolico.globalComponents.lazyColumn
import com.samuelsumbane.oremoscatolico.repository.isAndroid
import com.samuelsumbane.oremoscatolico.repository.PageName
import com.samuelsumbane.oremoscatolico.repository.isNumber
import com.samuelsumbane.oremoscatolico.view.sideBar.PreferencesWidget
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigScreenViewModel
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@Composable
fun Home(navigator: Navigator) {
    var textInputValue by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val inVertical by remember(screenWidth) {
        derivedStateOf { screenWidth - (screenWidth * 0.15) }
    }
    val inHorizontal by remember(screenHeight) {
        derivedStateOf { screenHeight }
    }

    var iconColorState by remember { mutableStateOf("Keep")}
    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        bottomBar = {
            if (isPortrait) BottomAppBarPrincipal(navigator,PageName.HOME.value, iconColorState)
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.oremosmobilepic),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(Color.Black.copy(alpha = 0.5f), Color.Transparent)))
            )

            if (!isPortrait) AppSideBar(navigator, "home")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(Modifier.height(140.dp))

                InputSearch(
                    value = textInputValue, onValueChange = { textInputValue = it },
                    placeholder = stringResource(R.string.search_song_or_pray),
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(45.dp)
//                                .background(color = searchBgColor, shape = RoundedCornerShape(35.dp)),
                )

                Spacer(Modifier.height(80.dp))
                AppTitleWidget(navigator)
            }

            HomeItems(
                navigator,
                textInputValue,
                modifier = Modifier.align(Alignment.CenterEnd),
            )
        }
    }
}
