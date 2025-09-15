package com.samuelsumbane.oremoscatolico.globalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.search
import org.jetbrains.compose.resources.painterResource

@Composable
fun InputSearch(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(color = Color.White, fontSize = 17.sp),
        modifier = modifier
            .fillMaxWidth(0.85f)
            .background(color = colorScheme.secondary, RoundedCornerShape(20.dp))
            .height(40.dp),
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(painter = painterResource(Res.drawable.search), contentDescription = "search Input", tint = Color.White, modifier = Modifier.padding(start = 7.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Box {
                    if (value.isEmpty()) {
                        Text(placeholder, color = Color.White, fontSize = 17.sp)
                    }
                    innerTextField()
                }
            }
        }
    ) //728347
}
