package com.samuelsumbane.oremoscatolico.desktopWidgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.samuelsumbane.oremoscatolico.globalComponents.InputSearch


@Composable
fun DesktopSearchContainer(
    searchInputLabel: String = "Pesquisar oração",
    searchResponse: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    Column(
        Modifier
            .width(320.dp)
            .height(40.dp)
//            .background(Color.Green)
    ){
        Row (
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            InputSearch(
                value = searchText,
                onValueChange = {
                    searchText = it
                    searchResponse(it)
                },
                placeholder = searchInputLabel,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
    }
}
