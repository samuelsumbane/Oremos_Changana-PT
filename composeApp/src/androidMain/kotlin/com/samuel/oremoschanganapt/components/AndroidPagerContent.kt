package com.samuel.oremoschanganapt.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.globalComponents.textFontSize
import com.samuel.oremoschanganapt.isMobilePortrait
import com.samuel.oremoschanganapt.repository.isDesktop
import com.samuel.oremoschanganapt.shortcutButtonWidget

@Composable
fun AndroidPagerContent(
    modifier: Modifier,
    navigator: Navigator,
    title: String,
    subTitle: String,
    body: String,
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
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(12.dp))

//                AndroidIncrementalTextParser(body)
                Text(
                    text = AnnotatedString.fromHtml(htmlString = body),
                    fontSize = textFontSize(),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }

        shortcutButtonWidget(navigator)
    }
}