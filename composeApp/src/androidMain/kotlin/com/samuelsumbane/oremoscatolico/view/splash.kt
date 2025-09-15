package com.samuelsumbane.oremoscatolico.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import com.samuelsumbane.oremoscatolico.HomeScreen
import com.samuelsumbane.oremoscatolico.R
import com.samuelsumbane.oremoscatolico.ui.theme.BlueColor
import com.samuelsumbane.oremoscatolico.ui.theme.splashColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SplashWindow(navigator: Navigator) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            delay(1000)
            navigator.push(HomeScreen)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(splashColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.oremospic),
            contentDescription = null,
            modifier = Modifier.padding(16.dp),
            contentScale = ContentScale.Fit
        )

        Text(text="Oremos ", color = BlueColor, fontSize = 27.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
        Text(text="Changana - Português", color = BlueColor, fontSize = 25.sp, fontStyle = FontStyle.Italic)
    }
}
