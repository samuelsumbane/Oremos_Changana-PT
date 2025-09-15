package com.samuelsumbane.oremoscatolico.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.samuelsumbane.oremoscatolico.globalComponents.SongRow
import com.samuelsumbane.oremoscatolico.globalComponents.lazyColumn
import com.samuelsumbane.oremoscatolico.isAndroid
import com.samuelsumbane.oremoscatolico.repository.PageName
import com.samuelsumbane.oremoscatolico.repository.isNumber
import com.samuelsumbane.oremoscatolico.view.sideBar.PreferencesWidget
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigScreenViewModel
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@Composable
fun Home(navigator: Navigator) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var textInputValue by remember { mutableStateOf("") }
    var showModal by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var themeColor = ColorObject.mainColor

    var mode by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    val allPrays = praysData

    val filteredPrays = remember(allPrays, textInputValue){
        if (textInputValue.isNotEmpty()) {
            allPrays.filter { it.title.contains(textInputValue, ignoreCase = true)}
        } else emptyList()
    }

    val filteredSongs = remember(songsData, textInputValue){
        if (textInputValue.isNotBlank()) {
            val numOrNot = isNumber(textInputValue)
            if (numOrNot) {
                songsData.filter { it.number == textInputValue }
            } else {
                songsData.filter {
                    it.title.contains(textInputValue, ignoreCase = true)
                }
            }
        } else emptyList()
    }

    var lovedSongsIds by remember { mutableStateOf(setOf<Int>()) }
    val configViewModel = remember { ConfigScreenViewModel(createSettings()) }

    LaunchedEffect(Unit) {
        val defaultConfigs = configViewModel.loadConfigurations()
        lovedSongsIds = defaultConfigs.favoriteSongs
        mode = defaultConfigs.themeMode

    }

    LaunchedEffect(textInputValue) {
        showModal = textInputValue != ""
    }

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

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                Modifier
                    .padding(end = 10.dp, top = 24.dp)
                    .statusBarsPadding()
                    .width(if (isPortrait) inVertical.dp else inHorizontal.dp )
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Column (
                    Modifier.fillMaxWidth(0.95f)
                        .padding(start = 12.dp)
                        .fillMaxHeight()
                        .verticalScroll(scrollState),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.configurations).uppercase(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)

                        IconButton(
                            onClick = { scope.launch { drawerState.close() } },
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close sidebar",
                                tint = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Spacer(Modifier.height(75.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(30.dp)) {
                            // appearance ------->>
                            AppearanceWidget(navigator, mode)
                            //
                            PreferencesWidget(navigator)
                            // About --------->>
                            RowAbout(navigator)
                        }
                    }

                }
            }
        }
    ) {
            Scaffold(
                bottomBar = {
                    if (isPortrait) BottomAppBarPrincipal(navigator,PageName.HOME.value, iconColorState)
                }
            ) {
                Box(Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.homepic),
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
                        Row (Modifier.fillMaxWidth()
                            .padding(top = 35.dp)
                        ) {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu",
                                    tint = Color.White, modifier = Modifier.size(30.dp))
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        InputSearch(
                            value = textInputValue, onValueChange = { textInputValue = it },
                            placeholder = stringResource(R.string.search_song_or_pray),
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .height(45.dp)
//                                .background(color = searchBgColor, shape = RoundedCornerShape(35.dp)),
                        )

                        Spacer(Modifier.height(20.dp))
                        Column {
                            HomeTexts(text = "Oremos", fontSize = 45)
                            Spacer(modifier = Modifier.height(9.dp))
                            HomeTexts(text = "A HI KHONGELENI", fontSize = 23)
                        }
                    }

                    if (showModal) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.90f)
                                .heightIn(min = 90.dp, max = 500.dp)
                                .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(15.dp))
                                .align(Alignment.CenterEnd),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            lazyColumn {
                                items (filteredPrays) { pray ->
                                    PrayRow(
                                        navigator, pray = pray,
                                        showStarButton = false,
                                    )
                                }

                                items (filteredSongs) { song ->
                                    SongRow(
                                        navigator, song = song,
                                        blackBackground = true,
                                        showStarButton = false
                                    )
                                }
                            }
                        }
                    }
                }
            }

//        } else {
//            LoadingScreen()
//        }
    }

}


