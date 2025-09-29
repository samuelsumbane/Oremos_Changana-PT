package com.samuel.oremoschanganapt.globalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.data.praysData
import com.samuel.oremoschanganapt.db.data.songsData
import com.samuel.oremoschanganapt.repository.isDesktop
import com.samuel.oremoschanganapt.repository.isNumber

@Composable
fun HomeItems(
    navigator: Navigator,
    textInputValue: String,
    modifier: Modifier = Modifier,
) {
    val filteredPrays = remember(praysData, textInputValue){
        if (textInputValue.isNotEmpty()) {
            praysData.filter { it.title.contains(textInputValue, ignoreCase = true)}
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

    if (textInputValue.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth(if (isDesktop()) 0.40f else 0.90f)
                .heightIn(min = 90.dp, max = 500.dp)
                .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(15.dp)),
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
}