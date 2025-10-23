package com.samuel.oremoschanganapt.globalComponents

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.repository.FontSizeName
import com.samuel.oremoschanganapt.states.UIState.configFontSize
import com.samuel.oremoschanganapt.AditionalVerticalScroll
import com.samuel.oremoschanganapt.commonView.EachPageScreen
import com.samuel.oremoschanganapt.data.Pray
import com.samuel.oremoschanganapt.db.data.Song
import com.samuel.oremoschanganapt.isMobilePortrait
import com.samuel.oremoschanganapt.repository.DataCollection
import com.samuel.oremoschanganapt.repository.FontSize
import com.samuel.oremoschanganapt.repository.PageName
import com.samuel.oremoschanganapt.repository.isAndroid
import com.samuel.oremoschanganapt.repository.isDesktop
import com.samuel.oremoschanganapt.repository.parseStyledText
import com.samuel.oremoschanganapt.shortcutButtonWidget
import com.samuel.oremoschanganapt.ui.theme.Orange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.arrow_upward
import oremoschangana.composeapp.generated.resources.home
import oremoschangana.composeapp.generated.resources.ic_music
import oremoschangana.composeapp.generated.resources.keyboard_arrow_down
import oremoschangana.composeapp.generated.resources.keyboard_arrow_up
import oremoschangana.composeapp.generated.resources.loading
import oremoschangana.composeapp.generated.resources.more
import oremoschangana.composeapp.generated.resources.more_horiz
import oremoschangana.composeapp.generated.resources.prayicon
import oremoschangana.composeapp.generated.resources.prays
import oremoschangana.composeapp.generated.resources.settings
import oremoschangana.composeapp.generated.resources.songs
import oremoschangana.composeapp.generated.resources.star_fill
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeTexts(text: String, fontSize: Int) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = Color.White,
        fontSize = fontSize.sp,
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun SidebarNav(
    activePage: String,
    iconColorState: String = "Keep",
    onEachBtnClicked: (String) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val bottomBgColor = colorScheme.background

    NavigationRail(
        modifier = Modifier.fillMaxSize()
            .background(if (activePage == PageName.HOME.value) Color.Transparent else bottomBgColor),
        containerColor = Color.Transparent,
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(6.dp, 0.dp, 6.dp, 0.dp)
                    .sidebarHeight()
                    .width(80.dp)
                    .background(
                        bottomBgColor,
                        shape = RoundedCornerShape(15.dp)
                    ),
                elevation = CardDefaults.elevatedCardElevation(3.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .background(if (isDesktop()) colorScheme.secondary else Color.Transparent),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MenuContent(
                        activePage, iconColorState,
                        onMenuBtnClicked = { page ->
                            onEachBtnClicked(page)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun SongRow(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    song: Song,
    blackBackground: Boolean = false,
    loved: Boolean = false,
    showStarButton: Boolean = true,
    onToggleLoved: (Int) -> Unit = {},
) {
    val mainColor = ColorObject.mainColor
    var lovedIdSongs by remember { mutableStateOf( mutableSetOf<Int>()) }
//    var lovedState by remember { mutableStateOf(song.id in lovedIdSongs) }
//    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier
//            .fillMaxWidth()
            .height(55.dp)
            .clickable {
                navigator.push(EachPageScreen(DataCollection.SONGS, song.id))
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .size(40.dp)
                .height(60.dp)
                .border(1.dp, lerp(mainColor, ColorObject.secondColor, 0.3f), RoundedCornerShape(50))
                .align(Alignment.CenterVertically),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val songNumberColor = if (blackBackground) Color.White else MaterialTheme.colorScheme.tertiary
            Text(
                text = song.number,
                fontSize = (textFontSize().value - 5).sp,
                color = songNumberColor,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.width(6.dp))

        Row (
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            mainColor,
                            lerp(
                                start = mainColor,
                                stop = if (ColorObject.secondColor == Color.Transparent) ColorObject.mainColor else ColorObject.secondColor,
                                fraction = 0.9f
                            )
                        ),
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CommonRow(song.title, song.subTitle, Modifier.weight(1f))
            Row(Modifier.padding(end = 10.dp)) {
                if (showStarButton) {
                    StarButton(loved) {
                        onToggleLoved(song.id)
                    }
                }
            }
        }
    }
}


@Composable
fun lazyColumn(content:  LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) { content() }
}

@Composable
fun CommonRow(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontSize = textFontSize(),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )

        if (subTitle.isNotBlank()){
            val fontSizeUnit = textFontSize().value - 5
            Text(
                text = subTitle,
                fontSize = fontSizeUnit.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PrayRow(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    pray: Pray,
    loved: Boolean = false,
    showStarButton: Boolean = true,
    onToggleLoved: (Int) -> Unit = {},
) {
    val mainColor = ColorObject.mainColor
    val secondColor = ColorObject.secondColor

    with(pray) {
        Row(
            modifier = modifier
                .padding(start = if (isDesktop()) 8.dp else 0.dp, 0.dp, 0.dp, 0.dp)
//                .fillMaxSize()
                .height(55.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            mainColor,
                            lerp(
                                start = mainColor,
                                stop = if (secondColor == Color.Transparent) mainColor else secondColor,
                                fraction = 0.9f
                            )
                        ),
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable {
                    navigator.push(EachPageScreen(DataCollection.PRAYS, id))
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.95f)
                    .fillMaxHeight()
            ) {
                CommonRow(title, subTitle, Modifier.weight(1f))
            }

            Row(Modifier.padding(end = 10.dp)) {
                if (showStarButton) {
                    StarButton(loved) {
                        onToggleLoved(pray.id)
                    }
                }
            }
        }
    }
}


@Composable
fun StarButton(
    lovedState: Boolean,
    onClick: () -> Unit
) {
    // Icon size animation ------->>
    val scale = remember { androidx.compose.animation.core.Animatable(1f) }
    val iconColor by animateColorAsState(
        targetValue = if (lovedState) Orange else MaterialTheme.colorScheme.tertiary,
        animationSpec = tween(durationMillis = 300)
    )

    LaunchedEffect(lovedState) {
        if (lovedState) {
            // Execute animation scale when "Loving" ------>>
            scale.animateTo(
                targetValue = 1.5f, // Increase to 1.5x ------>>
                animationSpec = tween(durationMillis = 200)
            )
            scale.animateTo(
                targetValue = 1f, // Back to normal size ------->>
                animationSpec = tween(durationMillis = 200)
            )
        } else {
            // Sure that scale stays on normal size ------>>
            scale.snapTo(1f)
        }
    }

    IconButton(
        modifier = Modifier.size(35.dp),
        onClick = { onClick() },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Icon(
            painter = painterResource(if (lovedState) Res.drawable.star_fill else Res.drawable.star_fill),
            contentDescription = if (lovedState) "É favorito" else "Não é favorito",
            tint = iconColor,
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value
                )
        )
    }
}


@Composable
fun MenuContent(
    activePage: String,
    iconColorState: String = "Keep",
    onMenuBtnClicked: (String) -> Unit
) {
    val homePainter = painterResource(Res.drawable.home)
    val prayPainter = painterResource(Res.drawable.prayicon)
    val musicPainter = painterResource(Res.drawable.ic_music)
    val settingsPainter = painterResource(Res.drawable.settings)
    val morePainter = painterResource(Res.drawable.more_horiz)

    var mainColor by remember { mutableStateOf(ColorObject.mainColor) }
    LaunchedEffect(iconColorState) {
        if (iconColorState == "Reload") mainColor = ColorObject.mainColor // Updates icons color -------->>
    }

    val pages = buildList {
        add(PageName.HOME.value)
        add(PageName.PRAYS.value)
        add(PageName.SONGSGROUP.value)
        if (isDesktop()) add(PageName.SETTINGS.value)
        add(PageName.MOREPAGES.value)
    }

    val btnText = buildMap {
        set(PageName.HOME.value, stringResource(Res.string.home))
        set(PageName.PRAYS.value, stringResource(Res.string.prays))
        set(PageName.SONGSGROUP.value, stringResource(Res.string.songs))
        if (isDesktop()) set(PageName.SETTINGS.value, "Config...")
        set(PageName.MOREPAGES.value, stringResource(Res.string.more))
    }

    val btnIcons = buildMap {
        set(PageName.HOME.value, homePainter)
        set(PageName.PRAYS.value, prayPainter)
        set(PageName.SONGSGROUP.value, musicPainter)
        if (isDesktop()) { set(PageName.SETTINGS.value, settingsPainter) }
        set(PageName.MOREPAGES.value, morePainter)
    }

    pages.forEach {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .clickable { onMenuBtnClicked(it) }
                .drawWithContent {
                    drawContent()
                    if (activePage == it) {
                        drawLine(
                            color = lerp(
                                ColorObject.mainColor, Color.White, 0.15f
                            ),
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 3.dp.toPx(),
                        )
                    }
                }
        ) {
            Column(
                modifier = Modifier.width(60.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                btnIcons[it]?.let { painter ->
                    Icon(
                        painter = painter,
                        contentDescription = btnText[it],
                        modifier = Modifier.size(25.dp),
                        tint = ColorObject.mainColor
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = btnText[it]!!,
                    modifier = Modifier.padding(2.dp),
                    color = if (isDesktop() || activePage == PageName.HOME.value) Color.White else MaterialTheme.colorScheme.tertiary,
                    fontSize = if (isDesktop()) 10.sp else 13.sp
                )
            }
        }
    }
}
 

//@Composable
fun textFontSize() = FontSize.fromString(configFontSize).size


@Composable
fun IncrementalTextParser(rawBody: String) {
    var parsedParts by remember { mutableStateOf(listOf<AnnotatedString>()) }
    val strophe = remember(rawBody) { rawBody.split("\n\n") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(strophe) {
        parsedParts = emptyList()
        strophe.forEach { bloco ->
            val part = withContext(Dispatchers.Default) {
                parseStyledText(bloco)
            }
            parsedParts = parsedParts + part
            yield()
        }
        isLoading = false
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column {
            parsedParts.forEach { part ->
                Text(
                    text = part,
                    fontSize = textFontSize(),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }
    }
}



@Composable
fun PagerContent(
    modifier: Modifier,
    navigator: Navigator,
    title: String,
    subTitle: String,
    body: String,
    showShortcutButton: Boolean = true,
    onlyParseWhenVisible: Boolean = true
) {
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // For content
            Column(
                Modifier.fillMaxWidth(if (isDesktop() || !isMobilePortrait()) 0.5f else 1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = (textFontSize().value + 2).sp,
                    textAlign = TextAlign.Center,
                    softWrap = true,
                    modifier = Modifier.padding(20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = subTitle,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(12.dp))

//                IncrementalTextParser(body)
                if (onlyParseWhenVisible) {
                    IncrementalTextParser(body)
                } else {

                    Text(
                        text = body,
                        fontSize = textFontSize(),
                        textAlign = TextAlign.Justify,
                        modifier = modifier.padding(15.dp)
                    )
                }
            }
        }

        if (isDesktop()) AditionalVerticalScroll(
            modifier = Modifier.align(Alignment.CenterEnd),
            lazyListState = null, scrollState = scrollState
        )

        if (showShortcutButton) {
            shortcutButtonWidget(navigator)
        }

    }
}


@Composable
fun LoadingScreen() {
    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        val iconDefaultColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray

        CircularProgressIndicator(
            modifier = Modifier.width(54.dp),
            color = ColorObject.mainColor,
            trackColor = iconDefaultColor,
        )
        Spacer(Modifier.height(20.dp))
        Text("${stringResource(Res.string.loading)}...")
    }
}

@Preview
@Composable
fun MinCircular() {
    CircularProgressIndicator(
        modifier = Modifier.width(24.dp),
        color = ColorObject.mainColor,
        trackColor = Color.DarkGray,
    )
}

@Composable
fun ScrollToFirstItemBtn(modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        )
    ) {
        Icon(painterResource(Res.drawable.arrow_upward), contentDescription = "Scroll para cima", tint = MaterialTheme.colorScheme.background, modifier = Modifier.size(35.dp))
    }
}


@Composable
fun TextIconRow(title: String, showContent: Boolean, modifier: Modifier) {
    val mainColor = ColorObject.mainColor
    val rS = 9.dp // rowShape ---------->>

    Row (
        modifier = modifier
            .fillMaxSize()
            .height(45.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        mainColor,
                        lerp(
                            start = ColorObject.mainColor,
                            stop = if (ColorObject.secondColor == Color.Transparent) ColorObject.mainColor else ColorObject.secondColor,
                            fraction = 0.9f
                        )
                    ),
                ), shape = if (showContent)
                    RoundedCornerShape(rS, rS, 0.dp, 0.dp) else RoundedCornerShape(rS)
            ),
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
            if (showContent)
                Icon(painterResource(Res.drawable.keyboard_arrow_up), contentDescription = "Close", tint = Color.White)
            else
                Icon(painterResource(Res.drawable.keyboard_arrow_down), contentDescription = "Open", tint = Color.White)
        }
    }
}

@Composable
fun ItemRow(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(ColorObject.mainColor,
                        lerp(
                            start = ColorObject.mainColor,
                            stop = if (ColorObject.secondColor == Color.Transparent) ColorObject.mainColor else ColorObject.secondColor,
                            fraction = 0.9f
                        )
                    ),
                ), RoundedCornerShape(0.dp, 0.dp, 14.dp, 14.dp)
            ),
        horizontalAlignment = horizontalAlignment
    ) {
        content()
    }
}


@Composable
fun CenteratorForMorePages(
    innerValues: PaddingValues,
    content: @Composable () -> Unit
) {
    val bgColor = MaterialTheme.colorScheme.background

    Column(
        modifier = Modifier
            .padding(innerValues)
            .background(bgColor)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(Modifier.fillMaxWidth(if (isAndroid()) 1f else 0.4f)) {
            content()
        }
    }
}

@Composable
fun CenteratorForPageWithItems(content: @Composable () -> Unit) {
    Spacer(Modifier.padding(20.dp))
    Column(Modifier
        .fillMaxWidth(if (isAndroid()) 1f else 0.4f)
        .padding(top = 15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) { content() }
}


@Composable
fun DefTabButton(content: @Composable () -> Unit){
    Card(
        Modifier
            .fillMaxWidth()
//            .height(50.dp)
            .background(Color.Transparent, RoundedCornerShape(10.dp)),
        elevation = CardDefaults.elevatedCardElevation(3.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) { content() }
    }
}

@Composable
fun ExpandContentTabBtn(
    icon: Painter,
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorObject.mainColor,
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = "icon", tint = Color.White)
            Text(text = title, fontSize = textFontSize(), color = Color.White)
            Icon(
                painterResource(Res.drawable.keyboard_arrow_down),
                contentDescription = "Open ou close tab",
                tint = Color.White
            )
        }
    }
}


@Composable
fun RadioButtonDialog(
    showDialog: Boolean,
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val textColor = MaterialTheme.colorScheme.tertiary

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(title) },
            text = {
                Column {
                    options.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (option == selectedOption),
                                    onClick = { onOptionSelected(option) },
                                    role = Role.RadioButton
                                )
                                .padding(8.dp)
                        ) {
                            RadioButton(
                                selected = (option == selectedOption),
                                onClick = { onOptionSelected(option) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(option, color = textColor)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(18.dp),
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel", color = ColorObject.mainColor)
                }
            }
        )
    }
}


@Composable
fun KeyValueTextRow(
    key: String,
    value: String,
    onClick: () -> Unit
) {
    Row (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
//            .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(9.dp))
            .height(30.dp)

            .clickable { onClick() },
        Arrangement.SpaceBetween
    ) {
        Text(key, fontSize = textFontSize())
        Text(value, fontSize = textFontSize(), fontWeight = FontWeight.SemiBold)
    }
}


fun Modifier.platformWidth(): Modifier {
    return if (isAndroid()) {
        this.fillMaxWidth(0.97f)
    } else {
        this.width(500.dp)
    }
}

@Composable
fun ConfigColumn(
    title: String,
    content: @Composable () -> Unit
) {
    Spacer(modifier = Modifier.height(30.dp))

    Column {
        Text(text = title, fontSize = textFontSize(), fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        content()
    }
}