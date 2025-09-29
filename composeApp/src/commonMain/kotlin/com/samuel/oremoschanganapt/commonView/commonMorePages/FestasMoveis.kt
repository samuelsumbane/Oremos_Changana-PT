package com.samuel.oremoschanganapt.commonView.commonMorePages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.globalComponents.CenteratorForPageWithItems
//import com.samuel.oremoschanganapt.repository.ColorObject
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.arrow_back
import oremoschangana.composeapp.generated.resources.go_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FestasMoveis(navigator: Navigator) {
    val scroll = rememberScrollState()

    val datas = listOf(
        arrayOf("2025", "C", "05/Mar", "20/Abril", "08/Junho", "22/Junh", "30/Nov"),
        arrayOf("2026", "A", "18/Fev", "05/Abril", "24/Maio", "07/Junh", "29/Nov"),
        arrayOf("2027", "B", "10/Fev", "28/Mar", "16/Maio", "30/Maio", "28/Nov"),
        arrayOf("2028", "C", "02/Mar", "16/Abril", "04/Junho", "18/Junh", "03/Dez"),
        arrayOf("2029", "A", "14/Fev", "01/Abril", "20/Maio", "03/Junh", "02/Dez"),
        arrayOf("2030", "B", "06/Mar", "21/Abril", "09/Junho", "23/Junh", "01/Dez"),
        arrayOf("2031", "C", "26/Fev", "13/Abril", "01/Junho", "15/Junh", "30/Nov"),
        arrayOf("2032", "A", "11/Fev", "28/Marc", "16/Maio", "30/Maio", "28/Nov"),
        arrayOf("2033", "B", "02/Mar", "17/Abril", "05/Junho", "19/Junh", "27/Nov"),
        arrayOf("2034", "C", "22/Fev", "09/Abril", "28/Maio", "11/Junh", "03/Dez"),
        arrayOf("2035", "A", "07/Fev", "25/Mar", "13/Maio", "27/Maio", "02/Dez"),
        arrayOf("2036", "B", "27/Fev", "13/Abril", "01/Junho", "15/Junh", "30/Nov"),
        arrayOf("2037", "C", "18/Fev", "05/Abril", "24/Maio", "07/Junh", "29/Nov"),
        arrayOf("2038", "A", "10/Mar", "25/Abril", "13/Junho", "27/Junh", "28/Nov")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Festas móveis", color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() } ){
                        Icon(painterResource(Res.drawable.arrow_back), contentDescription = stringResource(Res.string.go_back))
                    }
                }
            )
        },
    ) { innerValues ->
        val itemBgColor = ColorObject.mainColor
        val textColor = Color.White
        Column(
            modifier = Modifier
                .padding(innerValues)
                .fillMaxSize()
                .verticalScroll(scroll),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CenteratorForPageWithItems {
                datas.forEach{ row ->
                    Column(
                        Modifier.fillMaxWidth(0.9f)
                            .background(brush = Brush.horizontalGradient(
                                colors = listOf(itemBgColor,
                                    lerp(
                                        start = ColorObject.mainColor,
                                        stop = if (ColorObject.secondColor == Color.Transparent) ColorObject.mainColor else ColorObject.secondColor,
                                        fraction = 0.9f
                                    )
                                ),
                            ), RoundedCornerShape(10.dp))
                    ) {
                        Column(
                            Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(text = row[0], fontWeight = FontWeight.Bold, color = textColor)
                                Text(text = row[1], fontWeight = FontWeight.Bold, color = textColor)
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Cinzas :  ----------------------------------- ${row[2]}", color = textColor)
                                Text(text = "Pascoa :  ---------------------------------- ${row[3]}", color = textColor)
                                Text(text = "Pentecostes :  -------------------------- ${row[4]}", color = textColor)
                                Text(text = "C. Cristo :  -------------------------------- ${row[5]}", color = textColor)
                                Text(text = "Advento :  --------------------------------- ${row[6]}", color = textColor)
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }

        }

//        ShortcutsButton(navigator)
    }
}
