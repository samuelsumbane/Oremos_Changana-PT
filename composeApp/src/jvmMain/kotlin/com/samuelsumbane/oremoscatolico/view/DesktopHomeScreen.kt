package com.samuelsumbane.oremoscatolico.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
//import com.samuelsumbane.oremoscatolico.Lang
import com.samuelsumbane.oremoscatolico.data.praysData
import com.samuelsumbane.oremoscatolico.db.data.songsData
import com.samuelsumbane.oremoscatolico.globalComponents.AppSideBar
import com.samuelsumbane.oremoscatolico.globalComponents.AppTitleWidget
import com.samuelsumbane.oremoscatolico.globalComponents.HomeTexts
import com.samuelsumbane.oremoscatolico.globalComponents.InputSearch
import com.samuelsumbane.oremoscatolico.globalComponents.PrayRow
import com.samuelsumbane.oremoscatolico.globalComponents.SongRow
import com.samuelsumbane.oremoscatolico.globalComponents.lazyColumn
import com.samuelsumbane.oremoscatolico.repository.PageName
import com.samuelsumbane.oremoscatolico.repository.isNumber
//import oremoscatolico.composeapp.generated.resources.Oremos_desktop_wallpaper
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.oremosdesktoppic
import oremoscatolico.composeapp.generated.resources.search_song_or_pray
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun DesktopHomePage() {
        val showModal by remember { mutableStateOf(false) }
        val allPrays = praysData
        var textInputValue by remember { mutableStateOf("") }
        val navigator = LocalNavigator.currentOrThrow

        //
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }


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

        Scaffold(
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {

                Image(
                    painter = painterResource(Res.drawable.oremosdesktoppic),
                    contentDescription = "Home wallpaper",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(0.50f))
                )

                AppSideBar(navigator, PageName.HOME.value)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(Modifier.height(50.dp))

                    InputSearch(
                        value = textInputValue, onValueChange = { textInputValue = it },
                        placeholder = stringResource(Res.string.search_song_or_pray),
//                        placeholder = stringResource(Res.string.search_song_or_pray),
                        modifier = Modifier
                            .fillMaxWidth(0.35f)
                            .height(45.dp)
//                                .background(color = searchBgColor, shape = RoundedCornerShape(35.dp)),
                    )

                    Spacer(Modifier.height(170.dp))
                    AppTitleWidget(navigator)

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
                            items(filteredPrays) { pray ->
                                PrayRow(
                                    navigator, pray = pray,
                                    showStarButton = false,
                                )
                            }

                            items(filteredSongs) { song ->
                                SongRow(
                                    navigator, song = song,
                                    blackBackground = true,
                                    showStarButton = false
                                )
                            }
                        }
                    }
                }
//                    showSnackbar()
            }
        }

//    }
}


