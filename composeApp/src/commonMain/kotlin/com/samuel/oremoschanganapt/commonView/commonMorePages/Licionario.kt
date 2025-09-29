package com.samuel.oremoschanganapt.commonView.commonMorePages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.globalComponents.CenteratorForPageWithItems
import com.samuel.oremoschanganapt.globalComponents.ItemRow
import com.samuel.oremoschanganapt.globalComponents.TextIconRow
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.arrow_back
import oremoschangana.composeapp.generated.resources.go_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Licionario(navigator: Navigator) {
    val scroll = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Leccionário Litúrgico", color = MaterialTheme.colorScheme.tertiary) },
                 colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() } ) {
                        Icon(painterResource(Res.drawable.arrow_back), contentDescription = stringResource(Res.string.go_back))
                    }
                }
            )
        },
    ) { innerValues ->
        Column(
            modifier = Modifier
                .padding(innerValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scroll),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CenteratorForPageWithItems {
                bookList.forEach { BooksCard(it) }
            }
        }
//        ShortcutsButton(navigator)
    }
}

class BooksItem(val title: String, val books: String)

@Composable
fun BooksCard(dataList: List<BooksItem>) {
    val itemBgColor = ColorObject.mainColor
    val textColor = Color.White

    dataList.forEach { item ->
        var expanded by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 9.dp, end = 15.dp , bottom = 0.dp, start = 15.dp)
        ) {
            TextIconRow(item.title, expanded, modifier = Modifier.clickable { expanded = !expanded })

            AnimatedVisibility(expanded) {
                ItemRow(
                    modifier = Modifier.animateContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(item.books.replaceFirstChar{ it.uppercase() }, color = textColor)
                }
            }
        }
    }
}

val bookList: List<List<BooksItem>> = listOf(
    listOf(BooksItem("1° Domingo do Advento", "A Is. 2, 1-5     Rom. 13, 11-14      Mt. 24, 37-44\nB Is. 63, 16-64,  8 I Cor, 1,3-9  Mc. 13, 33-37\nC Jer. 33, 14-16  I Thes. 3, 12-4,2   Lc, 21, 25-36")),
    listOf(BooksItem("2° Domingo do Advento", "A Is. 11,1-10   Rom. 15,4-9   Mt. 3, 1-12\nB Is. 40, 1-11    2 Ped. 3,8-14    Mc. 1, 1-8\nC Baruc 5,   1-9 Flp. 1,4-11    Lc. 3, 1-6")),
    listOf(BooksItem("3° Domingo do Advento", "AIs. 35, 1-10    Tg. 5, 7-10    Mt, 11, 2-11\nB Is. 61,1-11    ITes. 5, 16-24    Jo. 1,6-8. 19-21\nC Sof. 3, 14-18   Flp. 4,4-7    Lc. 3, 10-18")),
    listOf(BooksItem("4° Domingo do Advento", "AIs. 7, 10-14  Rom. 1, 1-7  Mt. 1, 18-24\nB 2Lam. 7, 1-16 Rom. 16,25-27 Lc. 1, 26-38\nC Miq. 5,2-5 Heb. 10,5-10 Lc. 1, 39-47")),
    listOf(BooksItem("Sagrada Família (Dom. na oitava do Natal)", "A Eclo. 3, 3-7. 14-17  Col. 3, 12-21  Mt. 2, 13-23\nB Eclo. 3,3-7. 14-17  Col. 3, 12-21  Lc. 2, 22-40\nC Eclo. 3,3-7. 14-17  Col. 3, 12-21  Lc. 2, 41-52")),
    listOf(BooksItem("Baptismo do Senhor (10. Dom. Com.)", "AIs. 42, 1-7  Act. 10, 34-38  Mt. 3, 13-17\nB Is. 42, 1-7  Act. 20, 34-38  Mc. 1,6-11\nC Is. 42, 1-7  Act. 10, 34-38  Lc. 3, 15-22")),
    listOf(BooksItem("1° Domingo da Quaresma", "A Gen. 2,7-9;3,1-7  Rom. 5, 12-19  Mt. 4, 1-11\nB Gen. 9, 8-15  I Ped. 3, 18-22  Mc. 1, 12-15\nC Deut. 26, 4-10  Rom. 10, 8-13  Lc. 4, 1-13\n")),
    listOf(BooksItem("2° Domingo da Quaresma", "A Gen. 12, 1-4  2 tim. 1,8-10  Mt. 17, 1-9\\nB Gen. 22, 1-2.9-18  Rom. 8, 31-34  Mc. 9, 1-9\nC Gen. 15, 5-18  Flp. 3, 17-4,1  Lc. 9, 28-36")),
    listOf(BooksItem("3° Domingo da Quaresma", "A Ex. 17, 3-7  Rom. 5, 1-8  Jo. 4, 5-42\nB Ex. 20, 1-17  1 Cor. 1,22-25  Jo. 2, 13-15\nC Ex. 3, 1-15  1 Cor. 1-12  Lc. 13, 1-9")),
    listOf(BooksItem("4° Domingo da Quaresma", "A I Sam. 16, 1-13  Ef. 5,8-14  Jo. 9, 1-41\nB 2 Cron. 36, 14-23  Ef. 2,4-10  Jo. 3, 14-21\nC Jos. 5, 9-12  2 Cor. 5, 17-21  Lc. 15, 1-32")),
    listOf(BooksItem("5° Domingo da Quaresma", "A Ezeq. 37, 12-14  Rom. 8,8-11  Jo. 11,1-45\nB Jer. 31,31-44  Heb. 5,7-9  Jo. 12, 20-23\nC Is. 43, 16-21  Flp. 3,8-14  Jo. 8. 1-11")),
    listOf(BooksItem("Missa", "A Is. 50, 4-7  Flp. 2,6-11  Mt. 26, 14-27.66\nB Is. 50, 4-7  Flp. 2, 6-11  Mc. 14, 1-15.47\nC Is. 50, 4-7  Flp. 2,6-11  Lc. 22, 14-23.56")),
    listOf(BooksItem("5a Feira Santa (Missa vespertina)", "Ex. 12, 1-14\n1 Cor. 11, 23-26\nJo. 13, 1-15\n")),
    listOf(BooksItem("6a Feira Santa (Paixão do Senhor)","Is. 52, 13-53, 12\nHeb. 4, 14-16;5,7-9\nJo. 18, 1-19,42")),
    listOf(BooksItem("Sábado Santo (Vigília pascal)", "Gen.1,1-2-2\nEx. 14,15-15,1\nIs. 54, 1-14\nEzeq. 36, 16-28\nRom. 6, 3-11\nAMt. 28, 1-10\nnB Mc. 16, 1-8\nnC Lc. 24, 1-12")),
    listOf(BooksItem("natal", "Vig. Is. 62, 1-5 Act. 13, 16-17, 22-25 Mt. 1,1-25 (f. longa)\nMt. 1, 18-25 (f. breve)")),
    listOf(BooksItem("noite", "Is. 9, 2-7\nTito 2, 11-14\nLc. 2, 1-14\nAur. Is 62, 11-12\nTito, 3, 4-7\nLc. 2, 15-20\nDia Is. 52,7-10\nHeb. 1,1-6\nJo. 1,1-18")),
    listOf(BooksItem("Epifania (2o Dom. após o Natal)", "Is. 60, 1-6 \nEf. 3, 2-6 \nMat. 2, 1-12")),
    listOf(BooksItem("Santa Maria, Mãe de Deus (1 de Janeiro)", "num. 6, 22-27\nGal. 4, 4-7\nLc. 2, 16-21")),
    listOf(BooksItem("4a Feira de Cinzas", "Joel 2,12-18\nb2 Cor. 5, 20 - 6,2\nMt. 6, 1-18")),
    listOf(BooksItem("Domingo da Paixão - Ramos", "A Mt. 21, 1-11\nB Mc. 11, 1-10\nC Lc. 19, 28-40")),
    listOf(BooksItem("Domingo de Páscoa - Ressurreição", "A ct.10,34.37-43 \nCol. 3, 1-4 \nJo.20, 1-9\nou\nLc.24, 13-35")),
    listOf(BooksItem("2° Domingo da Páscoa", "A Act. 2, 42-47  1 Ped. 1,3-9  Jo.20, 19-31\nB Act. 4, 32-35  1 Jo. 5, 1-6  Jo.20, 19-31\nC Act. 5, 12-16  Apoc. 1,9-19  Jo.20, 19-31")),
    listOf(BooksItem("3° Domingo da Páscoa", "A Act. 2, 14.22-28  1 Ped. 1, 17-21  Lc.24, 13-35\nB Act. 3, 13-19  1 Jo. 2, 1-5  Lc.24, 35-48\nC Act 5, 27-32.40-41  Apoc. 5, 11-14  Jo.21, 1-19")),
    listOf(BooksItem("4° Domingo da Páscoa", "A Act. 2, 14.36-41   1 Ped. 2, 20-25\nB Act. 4, 8-11   1 Jo. 3, 1-2\nC Act. 13, 14.43-52 Apoc. ?, 9-17")),
    listOf(BooksItem("5° Domingo da Páscoa", "A Act. 6, 1-7  1 Ped. 2, 4-9\nB Act. 9, 26-31 1 Jo. 3, 18-24\nC Act 14, 20-26 Apoc. 21,1-5")),
    listOf(BooksItem("6° Domingo da Páscoa", "A Act. 8, 5-17  1 Ped. 3, 15-18  Jo. 14, 15-21\\nB Act. 10, 25-35, 44-48 1   Jo. 4, 7-10  Jo. 15, 9-17\\nC Act. 15, 1-2.22-29  Apoc. 21, 10-23  Jo. 14, 23-29")),
    listOf(BooksItem("Ascensão (7. Domingo da Páscoa)", "A Act. 1,1-11  Ef. 1, 17-23  Mt. 28, 16-20\\nB Act. 1,1-11  Ef. 1, 17-23  Lc. 24, 46-53\\nC Act. 1,1-11  Ef. 1, 17-23  Lc. 24, 46-53")),
    listOf(BooksItem("Pentecostes", "Act. 2, 1-11 1 Cor. 12, 3b-7.12-13 Jo. 20, 19-23")),
    listOf(BooksItem("Santíssima Trindade", "A Ex. 34, 4-9   2 Cor. 13, 11-13   Jo. 3, 16-18\nB Deut. 4, 32-40   Rom. 8, 14-17   Mt. 28, 16-20\\nC Prov. 8, 22-31   Rom. 5, 1-5   Jo. 16, 12-15")),
    listOf(BooksItem("Corpo e Sangue de Cristo", "A Deut. 8,2-3.14-16  1 Cor. 10, 16-17  Jo. 6, 51-59\nB Ex. 24, 3-8   Heb. 9, 11-15   Mc. 14, 12-16.22-26\nC Gen. 14, 18-20   1 Cor. 11, 23-26   Lc. 9, 11-17")),
    listOf(BooksItem("2° Domingo do tempo comum", "AIs. 49, 3-6   1 Cor. 1,1-3   Jo. 1, 29-34\nB 1 Sam. 3,3-10.19   1 Cor. 6, 13-20   Jo. 1, 35-42\\nC Is. 62, 1-5   1 Cor. 12, 4-11   Jo. 2, 1-12")),
    listOf(BooksItem("3° Domingo do tempo comum", "A Is. 9, 1-4  1 Cor. 1,10-17  Mt. 4, 12-23\nB Jon. 3, 1-5.10  1 Cor. 7, 29-31  Mc. 1, 14-20\nC Neem. 8, 2-10  1 Cor. 12, 12-31 a  Lc. 1,1-4; 4,14-21")),
    listOf(BooksItem("4° Domingo do tempo comum", "A Sof. 2,3;3,12-13  1 Cor. 1,26-31  Mt. 5,1-12a\\nB Deut. 18, 15-20  1 Cor. 7, 32-35  Mc, 1, 21-28\\nC Jer. 1, 4-5.17-19  1 Cor. 12,31-13,13  Lc. 4, 21-30")),
    listOf(BooksItem("5° Domingo do tempo comum", "A Is. 58, 7-10  1 Cor. 2, 1-5  Mt. 5, 13-16\\nB Job 7, 1-7  1 Cor. 9, 16-23  Mc. 1,29-39\\nC Is. 6, 1-8  1 Cor. 15, 1-11  Lc. 5, 1-11")),
    listOf(BooksItem("6° Domingo do tempo comum", "A Eclo. 15, 16-21  1 Cor. 2, 6-10  Mt. 5, 17-37\\nB Lev. 13, 1-2.44-46  1 Cor. 10,31-11,1  Mc. 1, 40-45\\nC Jer. 17,5-8  1 Cor. 15, 12-20  Lc. 6, 17-26")),
    listOf(BooksItem("7° Domingo do tempo comum", "A Lev. 19, 1-2.17-18  1 Cor. 3, 16-23  Mt. 5, 38-48\\nB Is. 43, 18-25  2 Cor. 1, 18-22  Mc. 2, 1 -12\\nC 1 Sam. 26,2-13.22-  1 Cor. 15, 45-49  Lc. 6, 27-38")),
    listOf(BooksItem("8° Domingo do tempo comum", "A Is. 49, 14-15  1 Cor. 4, 1-5  Mt. 6, 24-34\\nB Os. 2, 14-20  2 Cor. 3, 1-6  Mc. 2, 18-22\\nC Eclo. 27, 5-8  1 Cor. 15, 54-58  Lc. 6, 39-43")),
    listOf(BooksItem("9° Domingo do tempo comum", "A Deut. 11, 18-28  Rom. 3, 21-28  Mt. 7,21-27\nB Deut, 5, 12-15  2 Cor. 4, 6-11  Mc. 2, 23-3,6\nC 1Reis8,41-43  Gal. 1, 1-10  Lc. 7,1-10")),
    listOf(BooksItem("10° Domingo do tempo comum", "A Os. 6,3-6  Rom. 4, 18-25  Mt. 9,9-13\nB Gen. 3, 9-15  2 Cor. 4, 13-5,1  Mc. 3, 20-35\nC 1 Reis 17, 17-24  Gal. 1, 11-19  Lc. 11-17")),
    listOf(BooksItem("11° Domingo do tempo comum", "A Ex. 19, 2-6  Rom. 5, 6-11  Mt. 9, 36-10,8\nB Ezeq. 17, 22-24   2 Cor. 5, 6-10   Mc. 4, 26-34\nC 2 Sam. 12, 7-13   Gal. 2, 16-21   Lc. 7, 36-8,3")),
    listOf(BooksItem("12° Domingo do tempo comum", "A Jer. 20, 10-13   Rom. 5, 12-15   Mt. 10, 26-33\nB Job 38, 1-11   2 Cor. 5, 14-17   Mc. 4, 35-40\nC Zac. 12, 10-11   Gal. 3, 26-29   Lc. 9, 18-24")),
    listOf(BooksItem("13° Domingo do tempo comum", "A 2 Reis 4, 8-16   Rom. 6, 3-11   Mt. 10, 37-42\nB Sab. 1,13-15;2,23-25   2 Cor. 8, 7-15   Mc. 5, 21-43\nC 1 Reis 19, 16-21   Gal. 5, 1.13-18   Lc. 9, 51-62")),
    listOf(BooksItem("14° Domingo do tempo comum", "A Zac. 9, 9-10   Rom. 8, 9-13   Mt. 11, 25-30\nB Ezeq. 2, 2-5   2 Cor. 12, 7-10   Mc. 6, 1-6\nC Is. 66, 10-14   Gal. 6, 14-18   Lc. 10, 1-12.17-20")),
    listOf(BooksItem("15° Domingo do tempo comum","A Is. 55, 10-11   Rom. 8, 18-23   Mt. 13, 1-23\nB Am. 7, 12-15   Ef. 1,3-10   Mc. 6, 7-13\nC Deut. 30, 10-14   Col. 1, 15-20   Lc. 10, 25-37")),
    listOf(BooksItem("16° Domingo do tempo comum","A Sab. 12, 13-19   Rom. 8, 26-27   Mt. 13, 24-43\nB Jer. 23, 1-6   Ef. 2, 13-18   Mc. 6, 30-34\nC Gen. 18, 1-10   Col. 1, 24-28   Lc. 10, 38-42")),
    listOf(BooksItem("17° Domingo do tempo comum","A 1 Reis 3, 5-12 Rom. 8, 28-30 Mt. 13, 44-52\nB 2 Reis 4, 42-44 Ef. 4, 1-6 Jo. 6, 1-15\nC Gen. 18, 20-32 Col. 2, 12-14 Lc. 11, 1-13")),
    listOf(BooksItem("18° Domingo do tempo comum","A Is. 55, 1-3 Rom. 8, 35-39 Mt. 14, 13-21\nB Ex. 16, 2-15 Ef. 4, 17-24 Jo. 6, 24-35\nC Ecle. 1,2;2, 21-23 Col. 3, 1-11 Lc. 12, 13-21")),
    listOf(BooksItem("19° Domingo do tempo comum","A 1 Reis 19, 9-13 Rom. 9, 1-5 Mt. 14,22-23\nB 1 Reis 19, 4-8 Ef. 4, 30-5,2 Jo. 6, 41-52\nC Sab. 18, 6-9 Heb. 11, 1-19 Lc. 12, 32-48")),
    listOf(BooksItem("20° Domingo do tempo comum","A Is. 56, 1 -7 Rom. 11,13-32  Mt. 15, 21 -28\nB Prov. 9, 1-6  Ef. 5, 15-20  Jo. 6, 51-59\nC Jer. 38, 4-10  Heb. 12, 1-4  Lc. 12, 49-57")),
    listOf(BooksItem("21° Domingo do tempo comum","A Is. 22, 19-23  Rom. 11, 33-36  Mt. 16, 13-20\nB Jos. 24, 1-18  Ef. 5, 21-32  Jo. 6, 61-70\nC Is. 66, 18-21  Heb. 12, 5-13  Lc. 13, 22-30")),
    listOf(BooksItem("22° Domingo do tempo comum","A Jer. 20, 7-9  Rom. 12, 1-2  Mt. 16, 21-27\nB Deut. 4, 1-8  Trag. 1,17-27  Mc. 7, 1-23\nC Eclo. 3, 19-31  Heb. 12, 18-24  Lc. 14, 1-14")),
    listOf(BooksItem("23° Domingo do tempo comum","A Ezeq. 33, 7-9  Rom. 13, 8-10  Mt. 18, 15-20\nB Is. 35, 4-7  Tiag. 2, 14-18  Mc. 7, 31-37\nC Sab. 9, 13-19  Film. 9-17  Lc. 14,25-33")),
    listOf(BooksItem("24° Domingo do tempo comum","A Eclo. 27, 33-28,9  Rom. 14, 7-9  Mt. 18, 21-35\nB Is. 50, 5-9  Tiag. 2, 14-18  Mt. 88, 27-35\nC Ex. 32, 7-14  1 Tim. 1, 12-17  Lc. 15, 1-32")),
    listOf(BooksItem("25° Domingo do tempo comum","A Is. 55, 6-9  Filp. 1, 20-27  Mt. 20, 1-16\nB Sab. 2, 17-20 Tiag. 3, 16-4,3 Mc. 9, 29-36\nC Am. 8, 4-7 1 Tim. 2, 1-8 Lc. 16, 1-13")),
    listOf(BooksItem("26° Domingo do tempo comum","A Ezeq. 18, 25-28 Flp. 2,1-11 Mt. 21, 28-32\nB Num. 11, 25-29 Tiag. 5, 1 -6 Mc. 9, 37-47\nC Am. 6, 1-7  1 Tim. 6, 11-16  Lc. 16, 19-31")),
    listOf(BooksItem("27° Domingo do tempo comum","A Is. 5, 1-7  Flp. 4, 6-9  Mt. 21, 33-43\nB Gen. 2, 18-24  Heb. 2, 9-11  Mc. 10, 2-16\nC Hab. 1, 2-3;2,2-4  2 Tim. 1, 6-14  Lc. 17, 5-10")),
    listOf(BooksItem("28° Domingo do tempo comum","A Is. 25, 6-10 Flp. 4, 12-20 Mt. 22, 1-14\nB Sab. 7,7-11 Heb. 4, 12-13 Mc. 10, 17-30\nC 2 Reis 5, 14-17 2 Tim. 2, 8-13 Lc. 17, 11-19")),
    listOf(BooksItem("29° Domingo do tempo comum","A Is. 45, 1-6 1 Tes. 1,1-5 Mt. 22, 15-21\nB Is. 53, 10-11 Heb. 4, 14-16 Mc. 10, 35-45\nC Ex. 17, 8-13 2 Tim. 3, 14-4,2 Lc. 18, 1-8")),
    listOf(BooksItem("30° Domingo do tempo comum","A Ex. 22, 21-27 1 Tes. 1,5-10 Mt. 22, 34-40\nB Jer. 31,7-9 Heb. 5, 1-6 Mc. 10, 46-52\nC Eclo. 35, 15-22 2 Tim. 4,6-8.16-18 Lc. 18, 9-14")),
    listOf(BooksItem("31° Domingo do tempo comum","A Mal. 1, 14-2, 10 1 Tes. 2, 7-13 Mt. 23, 1-12\nB Deut.6, 2-6 Heb. 7, 24-28 Mc. 12, 38-44\nC 2 Mac, 7, 1-2-9-14 2 Tes. 2, 15-3-5 Lc. 20, 27-38")),
    listOf(BooksItem("32° Domingo do tempo comum","A Sab. 6, 13-17 1 Tes. 4, 12-17 Mt. 25, 1-13\nB 1 Reis. 17, 10-16 Heb. 8, 24-28 Mc. 12, 38-44\nC 2 Mac, 7, 1-2.9-14 2 Tes. 2, 15-3-5 Lc. 20, 27-38")),
    listOf(BooksItem("33° Domingo do tempo comum","A Prov. 31,10-13.19-31 1 Tes. 5, 1-6 Mt. 25, 14-30\nB Dan. 12, 1-3 Heb. 10, 11-18 Mc. 13, 24-32\nC Mal. 4, 1 -2 2 Tes. 3, 7-12 Lc. 21, 5-19")),
    listOf(BooksItem("34° Domingo do tempo comum (Cristo Rei)","A Ezq. 34, 11-17 1 Cor. 15, 20-28 Mt. 25, 31-46\nB Dan. 7, 13-14 Apoc, 1,5-8 Jo. 18, 33-37\nC 2 Sam. 5, 1-3 Col. 1, 12-20 Lc. 23, 35-43"))
)
