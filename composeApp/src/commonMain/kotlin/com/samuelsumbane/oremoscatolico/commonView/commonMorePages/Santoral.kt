package com.samuelsumbane.oremoscatolico.commonView.commonMorePages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.samuelsumbane.oremoscatolico.globalComponents.CenteratorForPageWithItems
import com.samuelsumbane.oremoscatolico.globalComponents.ItemRow
import com.samuelsumbane.oremoscatolico.globalComponents.TextIconRow
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.arrow_back
import oremoscatolico.composeapp.generated.resources.go_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.collections.iterator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Santoral(navigator: Navigator) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Santoral", color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(painterResource(Res.drawable.arrow_back), contentDescription = stringResource(Res.string.go_back))
                    }
                }
            )
        },
    ) { innerValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerValues)
                .verticalScroll(rememberScrollState()),
            Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CenteratorForPageWithItems {
//                Spacer(Modifier.height(20.dp))
                Text("Festas e memórias dos Santos", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(20.dp))

                for ((month, dates) in monthAndDates) {
                    ListItem(month, dates)
                }

            }
        }
    }

}

val monthAndDates = mapOf(
    "Mês de Janeiro" to listOf(
        "Dia 18 - Nossa Senhora da Defesa",
        "Dia 20 - São Sebastião",
        "Dia 21 - Santa Inês",
        "Dia 31 - Dom Bosco"),

    "Mês de Fevereiro" to listOf(
        "Dia 03 - São Braz",
        "Dia 08 - Santa Josefina Bakhita",
        "Dia 09 - Santa Apolônia",
        "Dia 11 - Nossa Senhora de Lourdes"
    ),

    "Mês de Março" to listOf("Dia 15 - São Longuinho", "Dia 19 - São José"),

    "Mês de Abril" to listOf(
        "Dia 19 - Santo Expedito",
        "Dia 23 - São Jorge",
        "Dia 25 - São Marcos",
    ),

    "Mês de Maio" to listOf(
        "Dia 01 - São José Carpinteiro",
        "Dia 04 - São Peregrino",
        "Dia 13 - Nossa Senhora de Fátima",
        "Dia 19 - Santo Ivo",
        "Dia 22 - Santa Rita"
    ),

    "Mês de Junho" to listOf(
        "Dia 13 - Santo Antônio",
        "Dia 24 - São João Batista",
        "Dia 29 - São Paulo",
        "Dia 29 - São Pedro"
    ),

    "Mês de Julho" to listOf(
        "Dia 04 - Santa Isabel",
        "Dia 09 - Santa Paulina",
        "Dia 11 - São Bento",
        "Dia 12 - Santa Verônica",
        "Dia 13 - Nossa Senhora da Rosa Mística",
        "Dia 14 - São Camilo de Lellis",
        "Dia 16 - Nossa Senhora do Carmo",
        "Dia 25 - São Cristovão",
        "Dia 26 - Santa Ana",
        "Dia 29 - Santa Marta",
        "Dia 29 - São Lazaro"
    ),

    "Mês de Agosto" to listOf(
        "Dia 11 - Santa Clara",
        "Dia 11 - Santa Filomena",
        "Dia 15 - Nossa Senhora da Assunção",
        "Dia 15 - Nossa Senhora do Sorriso",
        "Dia 16 - São Roque",
        "Dia 18 - Santa Helena",
        "Dia 27 - Santa Mônica",
        "Dia 28 - Santo Agostinho",
        "Dia 30 - Santa Rosa de Lima",
    ),

    "Mês de Setembro" to listOf(
        "Dia 04 - Santa Rosália",
        "Dia 15 - Nossa Senhora da Piedade",
        "Dia 15 - Nossa Senhora das Angústias",
        "Dia 15 - Nossa Senhora das Dores",
        "Dia 15 - Nossa Senhora do Calvário",
        "Dia 15 - Nossa Senhora do Pranto",
        "Dia 19 - Nossa Senhora da Salete",
        "Dia 21 - São Mateus",
        "Dia 23 - Padre Pio",
        "Dia 24 - Nossa Senhora das Mercês",
        "Dia 26 - São Cosme e São Damião",
        "Dia 26 - São Cosme e Damião",
        "Dia 29 - São Miguel",
        "Dia 29 - São Gabriel",
        "Dia 29 - São Rafael",
    ),

    "Mês de Outubo" to listOf(
        "Dia 01 - Santa Teresa",
        "Dia 02 - Anjo da Guarda",
        "Dia 04 - São Francisco de Assis",
        "Dia 05 - São Benedito",
        "Dia 07 - Nossa Senhora do Rosário",
        "Dia 12 - Nossa Senhora Aparecida",
        "Dia 12 - Nossa Senhora de Nazaré",
        "Dia 16 - Santa Edwiges",
        "Dia 16 - São Geraldo",
        "Dia 18 - São Lucas",
        "Dia 25 - Frei Galvão",
        "Dia 28 - São Judas Tadeu",
    ),

    "Mês de Novembro" to listOf(
        "Dia 22 - Santa Cecília",
        "Dia 25 - Santa Catarina",
        "Dia 27 - Nossa Senhora das Graças",
        "Dia 30 - Santo André"
    ),

    "Mês de Dezembro" to listOf(
        "Dia 04 - Santa Barbara",
        "Dia 08 - Nossa Senhora da Conceição",
        "Dia 08 - Nossa Senhora Imaculada Conceição",
        "Dia 12 - Nossa Senhora de Guadalupe",
        "Dia 13 - Santa Luzia",
        "Dia 27 - São João",
    ),
)

@Composable
fun ListItem(
    title: String,
    dataList: List<String>
) {
    Column(modifier = Modifier.fillMaxSize(0.9f)) {
        var showContent by remember { mutableStateOf(false) }

        TextIconRow(title, showContent, modifier = Modifier.clickable { showContent = !showContent })
        AnimatedVisibility(showContent) {
            ItemRow{
                dataList.forEach { item ->
                    Row(Modifier.fillMaxWidth().padding(10.dp)) {
                        Text(item, textAlign = TextAlign.Left, color = Color.White)
                    }
                }
            }
        }
    }
}