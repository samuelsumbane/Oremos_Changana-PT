package com.samuelsumbane.oremoscatolico.components

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuelsumbane.oremoscatolico.R
import com.samuelsumbane.oremoscatolico.globalComponents.textFontSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


fun toastAlert(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT){
    val toast = Toast.makeText(context, text, duration)
    toast.show()
}


@Composable
fun commonItemRow(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    // For PrayRow and SongRow
    Row(
        modifier = Modifier
            .padding(8.dp, 0.dp, 0.dp, 0.dp)
            .fillMaxSize()
            .height(55.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        ColorObject.mainColor,
                        lerp(
                            start = ColorObject.mainColor,
                            stop = if (ColorObject.secondColor == Color.Unspecified) ColorObject.mainColor else ColorObject.secondColor,
                            fraction = 0.9f
                        )
                    ),
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}









//@Composable
//fun TextIconRow(title: String, showContent: Boolean, modifier: Modifier) {
//    val mainColor = ColorObject.mainColor
//    val rS = 9.dp // rowShape ---------->>
//
//    Row (
//        modifier = modifier
//            .fillMaxSize()
//            .height(45.dp)
//            .background(
//                brush = Brush.horizontalGradient(
//                    colors = listOf(
//                        mainColor,
//                        lerp(
//                            start = ColorObject.mainColor,
//                            stop = if (ColorObject.secondColor == Color.Unspecified) ColorObject.mainColor else ColorObject.secondColor,
//                            fraction = 0.9f
//                        )
//                    ),
//                ), shape = if (showContent)
//                    RoundedCornerShape(rS, rS, 0.dp, 0.dp) else RoundedCornerShape(rS)
//            ),
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(10.dp)
//                .fillMaxSize(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(title, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
//            if (showContent)
//                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Close", tint = Color.White)
//            else
//                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Open", tint = Color.White)
//        }
//    }
//}



@Composable
fun pagerContent(
    navigator: Navigator,
    modifier: Modifier,
    title: String,
    subTitle: String,
    body: String,
    showShortcutButton: Boolean = true
) {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
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

            Text(
                text = body.trimIndent(),
                fontSize = textFontSize(),
                softWrap = true,
                modifier = Modifier.padding(15.dp).fillMaxWidth(),
                textAlign = TextAlign.Justify,
            )
        }

//        if (showShortcutButton) ShortcutsButton(navigator)
    }
}

