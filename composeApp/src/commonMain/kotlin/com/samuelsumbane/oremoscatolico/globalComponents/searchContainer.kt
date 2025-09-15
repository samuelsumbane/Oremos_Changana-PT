//package com.samuelsumbane.oremoscatolico.globalComponents
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
////import com.samuelsumbane.oremoscatolico.view.states.AppState.isSearchInputVisible
//
//@Composable
//fun searchContainer(
//    searchInputLabel: String = "Pesquisar oração",
//    showIcon: Boolean = true,
//    searchResponse: (String) -> Unit
//) {
//    var activeContainer by remember { mutableStateOf(false) }
////    val configuration = LocalConfiguration.current
////    val screenWidth = configuration.screenWidthDp
//
//    var searchText by remember { mutableStateOf("") }
//    var setFocus by remember { mutableStateOf(true) }
//
//    Column(
//        Modifier
//            .width(if (activeContainer) columnW.dp else 32.dp)
//            .height(64.dp)
//            .clickable {
//                activeContainer = !activeContainer
//                setFocus = activeContainer
//                isSearchInputVisible = activeContainer == true
//            }
//    ){
//        if (activeContainer) {
//            Row (
//                Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                InputSearch(
//                    value = searchText,
//                    onValueChange = {
//                        searchText = it
//                        searchResponse(it)
//                    },
//                    placeholder = searchInputLabel,
//                    modifier = Modifier.align(Alignment.CenterVertically),
//                )
//
//                if (showIcon) {
//                    Column(Modifier.fillMaxHeight()
//                        .width(30.dp),
//                        verticalArrangement = Arrangement.Center
//                    ){
//                        IconButton(onClick = {
//                            activeContainer = !activeContainer
//                            isSearchInputVisible = !isSearchInputVisible
//                        }){
//                            Icon(Icons.Default.KeyboardArrowRight, contentDescription="Close search input",
//                                modifier = Modifier.width(30.dp).fillMaxHeight(0.9f)
//                            )
//                        }
//                    }
//                }
//            }
//
//        } else {
//            Column( Modifier.fillMaxHeight(),
//                verticalArrangement = Arrangement.Center
//            ){
//                Spacer(Modifier.height(10.dp))
//                Icon(
//                    Icons.Default.Search,
//                    contentDescription="Search",
//                    tint = MaterialTheme.colorScheme.tertiary,
//                    modifier = Modifier.size(30.dp),
//                )
//            }
//        }
//    }
//}
