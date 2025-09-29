package com.samuel.oremoschanganapt.view.morepagesPackage

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.globalComponents.CenteratorForMorePages
import com.samuel.oremoschanganapt.globalComponents.ItemRow
import com.samuel.oremoschanganapt.globalComponents.TextIconRow
import com.samuel.oremoschanganapt.repository.isAndroid
//import com.samuel.oremoschanganapt.repository.ColorObject
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.arrow_back
import oremoschangana.composeapp.generated.resources.go_back
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Apendice(navigator: Navigator){
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Changana", "Português")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Apêndice", color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() } ){
                        Icon(painter = org.jetbrains.compose.resources.painterResource(Res.drawable.arrow_back), contentDescription = stringResource(Res.string.go_back))
                    }
                }
            )
        },
    ) { innerValues ->
        val bgColor = MaterialTheme.colorScheme.background

        CenteratorForMorePages(innerValues) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.padding(10.dp)
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        text = { Text(tab, style = MaterialTheme.typography.bodyMedium,
                            color = if (selectedTabIndex == index) Color.White else MaterialTheme.colorScheme.tertiary
                        ) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        modifier = Modifier
                            .background(
                                color = if (selectedTabIndex == index) ColorObject.mainColor else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            ),
                    )
                }
            }
            AnimatedContent(
                targetState = selectedTabIndex,
                transitionSpec = {
                    slideIntoContainer(
                        animationSpec = tween(400, easing = EaseIn),
                        towards = Up
                    ).togetherWith(
                        slideOutOfContainer(
                            animationSpec = tween(450, easing = EaseOut),
                            towards = Down
                        )
                    )
                },
            ) { selectedTabIndex ->
                when (selectedTabIndex) {
                    0 -> ChanganaTabContent(bgColor)
                    1 -> PtTabContent(bgColor)
                }
            }

        }
//        ShortcutsButton(navigator)
    }
}

class Item(val title: String, val subTitle: String)


@Composable
fun ListWidget(
    title: String,
    dataList: List<List<Item>>
) {
    val mainColor = ColorObject.mainColor
    val textColor = Color.White

    Column(modifier = Modifier.fillMaxSize()) {
        var showContent by remember { mutableStateOf(false) }

        TextIconRow(title, showContent, modifier = Modifier.clickable { showContent = !showContent })
        AnimatedVisibility(showContent) {
            ItemRow {
                dataList.forEach { list ->
                    list.forEach { item ->
                        Row(
                            Modifier.fillMaxWidth().padding(5.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ){
                            Text(item.title, textAlign = TextAlign.Justify, color = textColor)
                            Text(item.subTitle, color = textColor)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PtTabContent(divColor: Color) {
    Column(
        modifier = Modifier.background(divColor)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp),
        Arrangement.spacedBy(20.dp)
    ) {

        ListWidget("Pentateuco", listOf(listOf(
            Item("Gen.", "Génesis"),
            Item("Ex.", "Êxodo"),
            Item("Lv.", "Levítico"),
            Item("Nm.", "Números"),
            Item("Dt.", "Deuteronómio"),
        )))

        ListWidget("Livros históricos", listOf(listOf(
            Item("Jos.", "Josué"),
            Item("Jz.", "Juízes"),
            Item("Rut.", "Rute"),
            Item("1 Sam.", "1 Samuel"),
            Item("2 Sam.", "2 Samuel"),
            Item("1 Rs.", "1 Reis"),
            Item("2 Rs.", "2 Reis"),
            Item("1 Cr.", "1 Crónicas"),
            Item("2 Cr.", "2 Crónicas"),
            Item("Esd.", "Esdras"),
            Item("Ne.", "Neemias"),
            Item("Tob.", "Tobias"),
            Item("Jdt.", "Judite"),
            Item("Est.", "Ester"),
            Item("1 Mac.", "1 Macabeus"),
            Item("2 Mac.", "2 Macabeus"),
        )))

        ListWidget("Livros sapienciais", listOf(listOf(
            Item("Job", "Job"),
            Item("SI.", "Salmos"),
            Item("Prov.", "Provérbios"),
            Item("Ecle.", "Eclesiastes"),
            Item("Cant.", "Cântico dos Cânticos"),
            Item("Sab.", "Sabedoria"),
            Item("Eclo.", "Eclesiástico"),
        )))

        ListWidget("Profetas", listOf(listOf(
            Item("Is.", "Isais"),
            Item("Jer.", "Jeremias"),
            Item("Lam.", "Lamentações"),
            Item("Bar.", "Baruc"),
            Item("Ez.", "Ezequiel"),
            Item("Dan.", "Daniel"),
            Item("Os.", "Oséias"),
            Item("JL.", "Joel"),
            Item("Am.", "Amós"),
            Item("Adb.", "Adbias"),
            Item("Jn.", "Jonas"),
            Item("Miq.", "Miqueias"),
            Item("Na.", "Naum"),
            Item("Hab.", "Habacuc"),
            Item("Sof.", "Sofonias"),
            Item("Ag.", "Ageu"),
            Item("Zac", "Zacarias"),
            Item("Mal.", "Malaquias"),
            Item("Mt.", "S. Mateus"),
            Item("Me.", "S. Marcos"),
            Item("Le.", "S. Lucas"),
            Item("Jo.", "S. Joao"),
            Item("Act.", "Actos dos Apostolos"),
            Item("Rom.", "Romanos"),
            Item("1 Cor.", "1 Corintos"),
            Item("2 Cor.", "2 Corintos"),
            Item("Gal.", "Galatas"),
            Item("Ef.", "Efesios"),
            Item("Fil.", "Filipenses"),
            Item("Col.", "Colossenses"),
            Item("1 Tes.", "Tessalonicenses"),
            Item("2 Tes.", "Tessalonicenses"),
            Item("1 Tim.", "Timoteo"),
            Item("2 Tim.", "Timoteo"),
            Item("Tit.", "Tito"),
            Item("Fim.", "Filemon"),
            Item("Heb.", "Hebreus"),
            Item("Tgo.", "S. Tiago"),
            Item("1 Ped", "S. Pedro"),
            Item("2 Ped.", "S. Pedro"),
            Item("1 Jo.", "S. Joao"),
            Item("2 Jo.", "S. Joao"),
            Item("3 Jo.", "S. Joao"),
            Item("Jud.", "S. Judas"),
            Item("Ap.", "Apocalipse")
        )))
    }
}


@Composable
fun ChanganaTabContent(divColor: Color) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(divColor)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        Arrangement.spacedBy(20.dp)
    ) {
        ListWidget("Pentateuco", listOf(listOf(
            Item("Gen.", "Genesa"),
            Item("Eks", "Eksoda"),
            Item("Levh", "Levhitika"),
            Item("Tinhl", "Tinhlayo"),
            Item("Deut.", "Deuteronome")
        )))

        ListWidget("Livros históricos", listOf(listOf(
            Item("Yox.", "Yoxuwa"),
            Item("Vayav.", "Vayavanyisi"),
            Item("Rhu.", "Rhuti"),
            Item("I Sam.", "I Samiyele"),
            Item("II Sam.", "II Samiyele"),
            Item("I Tih.", "I Tihosi"),
            Item("II Tih.", "II Tihosi"),
            Item("I Tikr.", "I Tikronika"),
            Item("II Tikr.", "II Tikronika"),
            Item("Esr.", "Esra"),
            Item("Neh.", "Nehemiya"),
            Item("Tob.", "Tobiase"),
            Item("Jdt.", "Judite"),
            Item("Est.", "Estere"),
            Item("1. Mac.", "Macabewu"),
            Item("2. Mac.", "Macabewu"),
        )))

        ListWidget("Livros sapienciais", listOf(listOf(
            Item("Yob.", "Yobo"),
            Item("Ps.", "Tipsalema"),
            Item("Swiv.", "Swivuriso"),
            Item("Mudj.", "Mudjondzisi"),
            Item("Ris.", "Risimu ra Tinsimu"),
            Item("Wuth.", "Wuthlary"),
            Item("Ecl.", "Eclesiastike"),
        )))

        ListWidget("Profetas", listOf(listOf(
            Item("Es.", "Esaya"),
            Item("Yer.", "Yeremiya"),
            Item("Swir.", "Swirilo swa Yeremiya"),
            Item("Bar.", "Baruke"),
            Item("Ez.", "Ezekiyele"),
            Item("Dan.", "Daniyele"),
            Item("Hos.", "Hosiya"),
            Item("Yow", "Yowele"),
            Item("Am.", "Amosi"),
            Item("Ob.", "Obadiya"),
            Item("Yon.", "Yonasi"),
            Item("Mik.", "Mikiya"),
            Item("Nah.", "Nahume"),
            Item("Hab.", "Habakuku"),
            Item("Zef.", "Zefaniya"),
            Item("Hag.", "Hagayi"),
            Item("Zak.", "Zakariya"),
            Item("Mal.", "Malakiya"),
            Item("Mt.", "Matewu"),
            Item("Mk.", "Marka"),
            Item("Lk.", "Luka"),
            Item("Yoh.", "Yohane"),
            Item("Mint.", "Mintirho ya Vaapostola"),
            Item("Rom.", "Va le Rhoma"),
            Item("1 Cor.", "Va le Korinto"),
            Item("2 Cor.", "Va le Korinto"),
            Item("Gal.", "Va le Galatiya"),
            Item("Ef.", "Va le Efesa"),
            Item("Fil.", "Va le Filipiya"),
            Item("Col.", "Va le Kolosa"),
            Item("I Tes.", "Va le Tesalonika"),
            Item("II Tes.", "Va le Tesalonika"),
            Item("I Tim", "Timotiya"),
            Item("II Tim", "Timotiya"),
            Item("Tit.", "Tito"),
            Item("Fim.", "Filemoni"),
            Item("Hev.", "Vaheveru"),
            Item("Yak.", "Yakobo"),
            Item("I Pet.", "Petro"),
            Item("II Pet.", "Petro"),
            Item("I Yoh.", "Yohane"),
            Item("II Yoh.", "Yohane"),
            Item("III Yoh.", "Yohane"),
            Item("Yud.", "Yuda"),
            Item("Nhlav.", "Nhlavutelo")
        )))
    }
}