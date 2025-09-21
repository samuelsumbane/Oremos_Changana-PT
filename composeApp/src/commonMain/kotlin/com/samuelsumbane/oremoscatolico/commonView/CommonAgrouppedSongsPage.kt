package com.samuelsumbane.oremoscatolico.commonView

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.samuelsumbane.oremoscatolico.AditionalVerticalScroll
import com.samuelsumbane.oremoscatolico.BottomNav
import com.samuelsumbane.oremoscatolico.db.data.groupValues
import com.samuelsumbane.oremoscatolico.db.data.songsData
import com.samuelsumbane.oremoscatolico.globalComponents.AppSideBar
import com.samuelsumbane.oremoscatolico.globalComponents.LoadingScreen
import com.samuelsumbane.oremoscatolico.globalComponents.platformWidth
import com.samuelsumbane.oremoscatolico.isDesktop
import com.samuelsumbane.oremoscatolico.repository.PageName
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.arrow_back
import oremoscatolico.composeapp.generated.resources.arrow_forward
import oremoscatolico.composeapp.generated.resources.grouped_songs
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
    val allSongs = songsData
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.grouped_songs), color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = { navigator.pop()}) {
                        Icon(painterResource(Res.drawable.arrow_back), contentDescription = null)
                    }
                },
            )
        },
        bottomBar = {
            BottomNav(navigator, PageName.SONGSGROUP.value)
        }
    ) { paddingVales ->

        val mainColor = ColorObject.mainColor
        val secondColor = ColorObject.secondColor
        val listState = rememberLazyListState()


        if(allSongs.isNotEmpty()){
            Row(Modifier.padding(paddingVales).fillMaxSize()) {
                if (isDesktop()) AppSideBar(navigator, PageName.SONGSGROUP.value)

                Row(modifier = Modifier.weight(1f)) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize(0.97f)
                            .padding(start = 10.dp, top = 10.dp, end = 12.dp, bottom = 10.dp)
                        ,
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
                                        .border(1.dp, mainColor, shape = RoundedCornerShape(14.dp))
                                        .clickable {
                                             navigator.push(SongsScreen("${group.key}", "${group.value}"))
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

                                        Icon(painterResource(Res.drawable.arrow_forward), contentDescription = "to go collection")
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