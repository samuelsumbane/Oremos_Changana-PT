package com.samuel.oremoschanganapt.commonView

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.AditionalVerticalScroll
import com.samuel.oremoschanganapt.BottomNav
import com.samuel.oremoschanganapt.data.groupValues
import com.samuel.oremoschanganapt.globalComponents.AppSideBar
import com.samuel.oremoschanganapt.globalComponents.LoadingScreen
import com.samuel.oremoschanganapt.globalComponents.platformWidth
import com.samuel.oremoschanganapt.repository.PageName
import com.samuel.oremoschanganapt.repository.isDesktop
import com.samuel.oremoschanganapt.songsList
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.arrow_back
import oremoschangana.composeapp.generated.resources.arrow_forward
import oremoschangana.composeapp.generated.resources.grouped_songs
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


object AgroupedSongsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        CommonAgroupedPage(navigator)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAgroupedPage(navigator: Navigator) {
    val allSongs = songsList
    val navigator = LocalNavigator.currentOrThrow

    Row {
        AppSideBar(navigator, PageName.SONGSGROUP.value)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.grouped_songs),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                painterResource(Res.drawable.arrow_back),
                                contentDescription = null
                            )
                        }
                    },
                )
            },
            bottomBar = {
                BottomNav(navigator, PageName.SONGSGROUP.value)
            }
        ) { paddingVales ->
            val mainColor = ColorObject.mainColor
            val listState = rememberLazyListState()

            if (allSongs.isNotEmpty()) {
                Row(Modifier.padding(paddingVales).fillMaxSize()) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxSize(1f)
                                .padding(start = 10.dp, top = 10.dp, end = 12.dp, bottom = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item(1) {
                                groupValues.forEach { group ->
                                    Row(
                                        modifier = Modifier
                                            .padding(10.dp)
//                                        .fillMaxWidth(0.6f)
                                            .platformWidth()
                                            .height(55.dp)
                                            .border(
                                                1.dp,
                                                mainColor,
                                                shape = RoundedCornerShape(14.dp)
                                            )
                                            .clickable {
                                                navigator.push(
                                                    SongsScreen(
                                                        "${group.key}",
                                                        "${group.value}"
                                                    )
                                                )
                                            },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxSize(0.9f),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = group.value
                                                    .uppercase()
                                                    .replace(" | ", "\n"),
                                                textAlign = TextAlign.Center,
                                                color = MaterialTheme.colorScheme.tertiary,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 13.sp
                                            )

                                            Icon(
                                                painterResource(Res.drawable.arrow_forward),
                                                contentDescription = "to go collection"
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        if (isDesktop()) AditionalVerticalScroll(
                            modifier = Modifier,
                            lazyListState = listState,
                            scrollState = null
                        )
                    }

                }
            } else {
                LoadingScreen()
            }
        }
    }
}