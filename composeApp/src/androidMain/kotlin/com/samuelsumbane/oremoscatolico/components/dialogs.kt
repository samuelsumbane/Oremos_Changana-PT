package com.samuelsumbane.oremoscatolico.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.samuel.oremoschanganapt.repository.ColorObject
//import com.samuelsumbane.oremoscatolico.repository.ColorObject

import java.util.Calendar







//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun selectDateOrTimeOptions(
//    onDismissRequest: () -> Unit,
//    textColor: Color = MaterialTheme.colorScheme.primary,
//    dateOrTime: String
//): Pair<Long, Boolean>{
//    var returnedDateOrTime by remember { mutableLongStateOf(0) }
//    var showValuePicker by remember { mutableStateOf(false) }
//
//    val listValue = if(dateOrTime == "Date")
//        listOf("Today", "Tomorrow", "Next week", "Next Month")
//    else
//        listOf("Morning (9:00)", "Noon (12:00)", "Afternoon (15:00)",
//        "Evening (18:00)", "Late evening (21:00)")
//
//    alertDialogWidget(onDismissRequest = onDismissRequest,
//        content = {
//            Column(
//                Modifier
//                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(12.dp))
//                    .padding(12.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                for(btn in listValue ){
//                    Spacer(Modifier.height(10.dp))
//                    Button(
//                        onClick = {
//                            returnedDateOrTime = if(dateOrTime == "Time")
//                                timeStringToLong(btn) else dateStringToLong(btn)
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                            .border(1.dp, Color.Black, shape = RoundedCornerShape(14.dp)),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.Transparent,
//                            contentColor = textColor
//                        )
//                    ) { Text(btn) }
//                }
//
//                Spacer(Modifier.height(10.dp))
//                // Pick date or time buttons (Pick a date and or Pick a time) ------->
//                Button(
//                    onClick = { showValuePicker = true },
//                    modifier = Modifier.fillMaxWidth()
//                        .border(1.dp, Color.Black, shape = RoundedCornerShape(14.dp)),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Transparent,
//                        contentColor = textColor
//                    )
//                ) {
//                    val btnText = if(dateOrTime == "Date") "Pick a date" else "Pick a time"
//                    Text(text = btnText)
//                }
//            }
//        }
//    )
//
//    LaunchedEffect(returnedDateOrTime, showValuePicker) {
//        if(returnedDateOrTime != 0L || showValuePicker){ onDismissRequest() }
//    }
//
//    return Pair(returnedDateOrTime, showValuePicker)
//}



@Composable
fun OkAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = { Icon(icon, contentDescription = "Alert Dialog Icon", tint = ColorObject.mainColor) },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text("Confirm", color = ColorObject.mainColor)
            }
        }
    )
}