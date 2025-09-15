package com.samuelsumbane.oremoscatolico.components.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.navigator.Navigator
import com.samuelsumbane.oremoscatolico.HomeScreen
import com.samuelsumbane.oremoscatolico.R
import com.samuelsumbane.oremoscatolico.createSettings
import com.samuelsumbane.oremoscatolico.db.data.songsData
import com.samuelsumbane.oremoscatolico.data.praysData
import com.samuelsumbane.oremoscatolico.repository.isNumber
import com.samuelsumbane.oremoscatolico.viewmodels.ConfigScreenViewModel
import kotlinx.coroutines.coroutineScope
import kotlin.math.roundToInt

@Composable
fun ShortcutsButton(navigator: Navigator) {

    var isActive by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.toDouble()
    val screenPercent = 10 * screenHeight / 100
    var searchValue by remember { mutableStateOf("") }

    val allPrays = praysData
    val allSongs = songsData

    val filteredPrays = remember(allPrays, searchValue){
        if (searchValue.isNotEmpty()) {
            allPrays.filter { it.title.contains(searchValue, ignoreCase = true)}
        } else {
            emptyList()
        }
    }

    val filteredSongs = remember(allSongs, searchValue){
        if (searchValue.isNotBlank()) {
            val numOrNot = isNumber(searchValue)
            if (numOrNot) {
                allSongs.filter { it.number == searchValue }
            } else {
                allSongs.filter {
                    it.title.contains(searchValue,ignoreCase = true)
                }
            }
        } else { emptyList() }
    }

    Box(
        modifier = Modifier
        .fillMaxSize()
//        .background(Color.Magenta)
    ) {
        var offsetY by remember { mutableDoubleStateOf(screenHeight) }
        val childColumnHeight by remember { mutableIntStateOf(150) }
        val context = LocalContext.current

        var lovedIdPrays by remember { mutableStateOf(setOf<Int>()) }
        var lovedSongsIds by remember { mutableStateOf(setOf<Int>()) }

//        val configViewModel = remember { ConfigScreenViewModel(createSettings()) }
        LaunchedEffect(Unit) {
//            val defaultConfigs = configViewModel.loadConfigurations()
//            lovedIdPrays = defaultConfigs.favoritePrays
//            lovedSongsIds = defaultConfigs.favoriteSongs
        }

        LaunchedEffect(offsetY) {
            coroutineScope {
                if(offsetY < screenPercent){
                    offsetY = screenPercent - 10
                } else if ( offsetY > screenHeight * 1.20){
                    offsetY = screenHeight * 1.17
                }
            }
        }

        Row(
            Modifier
                .align(Alignment.TopEnd)
                .fillMaxHeight()
                .zIndex(10.0f)
                .offset {IntOffset(0, offsetY.roundToInt())}
        ) {

            Row(
                modifier = Modifier.height(childColumnHeight.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Leftpart (Songs icon, Pray icon, star icon) -------->>
                AnimatedVisibility(isActive) {
                    Column(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .width(140.dp)
                            .background(
                                color = MaterialTheme.colorScheme.background.copy(alpha = 0.97f),
                                shape = RoundedCornerShape(10)
                            ),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Row(
                            Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Songs
                            ShortcutButtonChild(
                                icon = ImageVector.vectorResource(id = R.drawable.ic_music),
                                description = "Canticos",
                                iconModifier = Modifier.size(26.dp)
                            ) {
//                                navigator.navigate("canticosAgrupados")
                            }

                            // Prays
                            ShortcutButtonChild(
                                icon = ImageVector.vectorResource(id = R.drawable.prayicon),
                                description = "Prays",
                                iconModifier = Modifier.size(26.dp)
                            ) {
//                                navigator.navigate("oracoespage")
                            }
                        }

                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ShortcutButtonChild(
                                icon = Icons.Outlined.Star,
                                description = "Favoritos"
                            ) {
//                                navigator.navigate("favoritospage")
                            }

                            ShortcutButtonChild(
                                icon = Icons.Default.Home,
                                description = "Home"
                            ) {
                                navigator.push(HomeScreen)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Column {
//                    val bgColor = if (!isSystemInDarkTheme()) LightSecondary else DarkSecondary

                    IconButton(
                        modifier = Modifier
                            .size(50.dp)
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    offsetY += dragAmount.y
                                }
                            },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Black.copy(alpha = 0.90f)),
                        onClick = { isActive = !isActive }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.adjust_24),
                            contentDescription = "Shortcuts",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.width(5.dp))
        }
    }
}
