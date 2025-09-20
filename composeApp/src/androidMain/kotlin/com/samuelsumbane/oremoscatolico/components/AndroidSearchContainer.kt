package com.samuelsumbane.oremoscatolico.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.samuelsumbane.oremoscatolico.globalComponents.InputSearch

@Composable
fun AndroidSearchContainer(
    searchInputLabel: String = "Pesquisar oração",
    isContainerActive: Boolean = false,
    showIcon: Boolean = true,
    searchValue: (String) -> Unit
) {
    var activeContainer by remember { mutableStateOf(isContainerActive) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val columnW by remember(screenWidth) {
        derivedStateOf { screenWidth - (screenWidth * 0.35) }
    }

    var searchText by remember { mutableStateOf("") }
    var setFocus by remember { mutableStateOf(true) }

    Column(
        Modifier
            .width(if (activeContainer) columnW.dp else 32.dp)
            .height(64.dp)
            .clickable {
                activeContainer = !activeContainer
                setFocus = activeContainer
            }
    ){
        if (activeContainer) {
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

                if (showIcon) {
                    Column(Modifier.fillMaxHeight()
                        .width(30.dp),
                        verticalArrangement = Arrangement.Center
                    ){
                        IconButton(onClick = { activeContainer = !activeContainer }){
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription="Close search input",
                                modifier = Modifier.width(30.dp).fillMaxHeight(0.9f)
                            )
                        }
                    }
                }
            }

        } else {
            Column( Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ){
                Spacer(Modifier.height(10.dp))
                Icon(Icons.Default.Search, contentDescription="Search",
                    modifier = Modifier.size(30.dp),
                )
            }
        }
    }
}
