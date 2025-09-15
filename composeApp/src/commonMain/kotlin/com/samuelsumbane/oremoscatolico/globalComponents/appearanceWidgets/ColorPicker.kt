package com.samuelsumbane.oremoscatolico.globalComponents.appearanceWidgets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuelsumbane.oremoscatolico.isDesktop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.gradient
import oremoscatolico.composeapp.generated.resources.principal_color
import oremoscatolico.composeapp.generated.resources.secondary_color
import oremoscatolico.composeapp.generated.resources.solid
import org.jetbrains.compose.resources.stringResource

@Composable
fun ColorPickerHSV(
    modifier: Modifier = Modifier,
    size: Int = 256,
    initialColor: Color = Color.Green,
    isSolidColorTabSelected: (Boolean) -> Unit,
    onColorChanged: (Color) -> Unit,
    onSecondColorChanged: (Color) -> Unit
) {
    var hue by remember { mutableStateOf(0f) }
    var hue2 by remember { mutableStateOf(0f) }
    var saturation by remember { mutableStateOf(1f) }
    var saturation2 by remember { mutableStateOf(1f) }
    val value = 1f // luminosity fixed value

    var selectorPosition by remember { mutableStateOf(Offset.Zero) }
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val selectedColor = Color.hsv(hue, saturation, value)
    val secondSelectedColor = Color.hsv(hue2, saturation2, value)

//    val configuration = LocalConfiguration.current
//    val screenWidth = configuration.screenWidthDp
    var setSecondColor by remember {
        mutableStateOf(ColorObject.secondColor != Color.Unspecified)
    }

//    val columnW by remember(screenWidth) {
//        derivedStateOf { screenWidth - (screenWidth * 0.35) }
//    }

    val columnW = 300.dp

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(Res.string.solid),
        stringResource(Res.string.gradient),
    )
    val typography = MaterialTheme.typography
    var firstColorBoxSelected by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val hsvArray = FloatArray(3)
//        android.graphics.Color.colorToHSV(initialColor.toArgb(), hsvArray)
        hue = hsvArray[0]
        saturation = hsvArray[1]

        val x = (hue / 360f) * (size - 1)
        val y = saturation * (size - 1)
        selectorPosition = Offset(x, y)

        isSolidColorTabSelected(true)
        onColorChanged(Color.hsv(hue, saturation, value))
        onSecondColorChanged(Color.hsv(hue2, saturation2, value))
    }

    LaunchedEffect(value) {
        withContext(Dispatchers.Default) {
            val bmp = ImageBitmap(size, size)
            val canvas = androidx.compose.ui.graphics.Canvas(bmp)
            val paint = Paint()
            for (x in 0 until size) {
                for (y in 0 until size) {
                    val h = (x / (size - 1f)) * 360f
                    val s = y / (size - 1f)
                    paint.color = Color.hsv(h, s, value)
                    canvas.drawRect(Rect(x.toFloat(), y.toFloat(), x + 1f, y + 1f), paint)
                }
            }
            bitmap = bmp
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Spacer(Modifier.height(60.dp))
        Box(
            modifier = Modifier.size(200.dp).pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val x = change.position.x.coerceIn(0f, size - 1f)
                    val y = change.position.y.coerceIn(0f, size - 1f)

                    selectorPosition = Offset(x, y)
                    if (firstColorBoxSelected) {
                        hue = (x / (size - 1f)) * 360f
                        saturation = y / (size - 1f)
                        onColorChanged(Color.hsv(hue, saturation, value))
                    } else {
                        hue2 = (x / (size - 1f)) * 360f
                        saturation2 = y / (size - 1f)
                        onSecondColorChanged(Color.hsv(hue2, saturation2, value))
                    }
                }
            }) {
            if (bitmap != null) {
                Image(bitmap = bitmap!!, contentDescription = null)

                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color.White,
                        radius = 10.dp.toPx(),
                        center = selectorPosition,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        @Composable
        fun FirstColorPreviewer() {
            Spacer(Modifier.height(18.dp))
            spaceAroundContentWidget {
                Text(text = stringResource(Res.string.principal_color))
                colorSelectBox(
                    color = selectedColor,
                    selected = firstColorBoxSelected,
                ) { firstColorBoxSelected = true }
            }
        }

        Spacer(Modifier.height(20.dp))

        Column (
            modifier = Modifier.fillMaxWidth(if (isDesktop()) 0.45f else 1f)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex, modifier = Modifier.padding(10.dp)
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        text = {
                            Text(
                                text = tab,
                                style = typography.bodyMedium,
                                fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (selectedTabIndex == index) Color.White else MaterialTheme.colorScheme.tertiary
                            )
                        },
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index

                            firstColorBoxSelected =
                                if (index == 0) {
                                    onSecondColorChanged(Color.Unspecified)
                                    isSolidColorTabSelected(true)
                                    true
                                } else {
                                    isSolidColorTabSelected(false)
                                    false
                                }
                        },
                        modifier = Modifier
                            .background(
                                color = if (selectedTabIndex == index) ColorObject.mainColor else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        selectedContentColor = ColorObject.mainColor,
                    )
                }
            }

            AnimatedContent(
                targetState = selectedTabIndex,
                transitionSpec = {
                    slideIntoContainer(
                        animationSpec = tween(400, easing = EaseIn), towards = Up
                    ).togetherWith(
                        slideOutOfContainer(
                            animationSpec = tween(450, easing = EaseOut), towards = Down
                        )
                    )
                },
            ) { selectedTabIndex ->
                Column(
                    modifier = Modifier.fillMaxSize(0.8f),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (selectedTabIndex) {
                        0 -> FirstColorPreviewer()
                        1 -> {
                            FirstColorPreviewer()

                            Spacer(Modifier.height(16.dp))

                            spaceAroundContentWidget {
                                Text(text = stringResource(Res.string.secondary_color))
                                colorSelectBox(
                                    color = secondSelectedColor,
                                    selected = !firstColorBoxSelected,
                                ) { firstColorBoxSelected = false }
                            }
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                }
            }
        }

    }
}


@Composable
fun colorSelectBox(
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(60.dp)
            .background(color, shape = CircleShape)
            .border(
                width = if (selected) 4.dp else 1.dp,
                color = if (selected) Color.Gray else Color.Black,
                shape = CircleShape)
            .clickable { onClick() }
    )
}

@Composable
fun spaceAroundContentWidget(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize(0.8f),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) { content() }
}


@Composable
fun RowPreviewColor(color: Color) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(0.9f)
            .height(48.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        color,
                        lerp(
                            start = color,
                            stop = if (ColorObject.secondColor == Color.Transparent) color else ColorObject.secondColor,
                            fraction = 0.9f
                        )
                    ),
                ),
                shape = RoundedCornerShape(25)
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Apenas para previsualização", color = Color.White)
    }
}
