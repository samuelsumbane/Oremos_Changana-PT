package com.samuel.oremoschanganapt.view

//import com.samuel.oremoschanganapt.Lang
//import oremoschangana.composeapp.generated.resources.Oremos_desktop_wallpaper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuel.oremoschanganapt.globalComponents.AppSideBar
import com.samuel.oremoschanganapt.globalComponents.AppTitleWidget
import com.samuel.oremoschanganapt.globalComponents.HomeItems
import com.samuel.oremoschanganapt.globalComponents.InputSearch
import com.samuel.oremoschanganapt.repository.PageName
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.oremosdesktoppic
import oremoschangana.composeapp.generated.resources.search_song_or_pray
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun DesktopHomePage() {
        var textInputValue by remember { mutableStateOf("") }
        val navigator = LocalNavigator.currentOrThrow
        val snackbarHostState = remember { SnackbarHostState() }

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

                HomeItems(
                    navigator,
                    textInputValue,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                    ,
                )
            }
        }
//    }
}


