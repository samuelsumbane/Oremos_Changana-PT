package com.samuelsumbane.oremoscatolico.desktopWidgets

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samuelsumbane.oremoscatolico.globalComponents.InputSearch


@Composable
fun DesktopSearchContainer(
    searchInputLabel: String = "Pesquisar oração",
    searchValue: (String) -> Unit
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
                    searchValue(it)
                },
                placeholder = searchInputLabel,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
    }
}
