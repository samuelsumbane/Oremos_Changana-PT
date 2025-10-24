package com.samuel.oremoschanganapt.components

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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.HomeScreen
import com.samuel.oremoschanganapt.commonView.AgroupedSongsScreen
import com.samuel.oremoschanganapt.commonView.LovedDataScreen
import com.samuel.oremoschanganapt.commonView.PraysScreen
import com.samuel.oremoschanganapt.data.androidpraysList
import com.samuel.oremoschanganapt.repository.isNumber
import com.samuel.oremoschanganapt.songsList
import kotlinx.coroutines.coroutineScope
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.adjust_24
import oremoschangana.composeapp.generated.resources.home
import oremoschangana.composeapp.generated.resources.ic_music
import oremoschangana.composeapp.generated.resources.prayicon
import oremoschangana.composeapp.generated.resources.star_fill
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

@Composable
fun ShortcutsButton(navigator: Navigator) {

    var isActive by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.toDouble()
    val screenPercent = 10 * screenHeight / 100
    var searchValue by remember { mutableStateOf("") }

    val allPrays = androidpraysList
    val allSongs = songsList

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
                    it.title.contains(searchValue, ignoreCase = true)
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
                                icon = painterResource(Res.drawable.ic_music),
                                description = "Canticos",
                                iconModifier = Modifier.size(26.dp)
                            ) {
                                navigator.push(AgroupedSongsScreen)
                            }

                            // Prays
                            ShortcutButtonChild(
                                icon = painterResource(Res.drawable.prayicon),
                                description = "Prays",
                                iconModifier = Modifier.size(26.dp)
                            ) {
                                navigator.push(PraysScreen)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ShortcutButtonChild(
                                icon = painterResource(Res.drawable.star_fill),
                                description = "Favoritos"
                            ) {
                                navigator.push(LovedDataScreen)
                            }

                            ShortcutButtonChild(
                                icon = painterResource(Res.drawable.home),
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
                            painterResource(Res.drawable.adjust_24),
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


@Composable
fun ShortcutButtonChild(
    modifier: Modifier = Modifier,
    icon: Painter,
    description: String,
    iconModifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier.then(
            Modifier
                .size(45.dp)
                .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(15.dp))
        ),
        onClick = onClick
    ) {
        Icon(icon, contentDescription = description, modifier = iconModifier, tint = MaterialTheme.colorScheme.tertiary)
    }
}