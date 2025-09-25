package com.samuelsumbane.oremoscatolico.view.RemindersPages


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuelsumbane.oremoscatolico.Reminder
import com.samuelsumbane.oremoscatolico.ReminderRepository
import com.samuelsumbane.oremoscatolico.commonDialogs.DatePickerModalInput
import com.samuelsumbane.oremoscatolico.commonDialogs.TimePickerDialog
import com.samuelsumbane.oremoscatolico.globalComponents.CancelButton
import com.samuelsumbane.oremoscatolico.globalComponents.NormalButton
import com.samuelsumbane.oremoscatolico.globalComponents.platformWidth
import com.samuelsumbane.oremoscatolico.globalComponents.showSnackbar
import com.samuelsumbane.oremoscatolico.globalComponents.submitButtonsRow
import com.samuelsumbane.oremoscatolico.repository.combineTimestamps
import com.samuelsumbane.oremoscatolico.repository.convertLongToDateString
import com.samuelsumbane.oremoscatolico.repository.convertLongToTimeString
import com.samuelsumbane.oremoscatolico.repository.convertTimePickerStateToLong
import com.samuelsumbane.oremoscatolico.repository.getCurrentTimestamp
import com.samuelsumbane.oremoscatolico.repository.splitTimestamp

import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.arrow_back
import org.jetbrains.compose.resources.painterResource

//import com.samuelsumbane.oremoscatolico.repository.ColorObject

//@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigureReminder(
    navigator: Navigator,
    itemId: Int,
    table: String,
//    rdatetime: Long,
    reminderIdParam: Long = 0L,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Definir lembrete", color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() } ){
                        Icon(painterResource(Res.drawable.arrow_back), contentDescription = "Go back")
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingVales ->
//        val context = LocalContext.current
//        val reminderRepo = ReminderRepository(context)
        val reminderRepo = ReminderRepository()
        val reminders = reminderRepo.getAll()

        val possiblyReminder = reminders.firstOrNull { it.reminderData == itemId && it.reminderTable == table }

        var reminderdate by remember { mutableStateOf(getCurrentTimestamp()) }
        var remindertime by remember { mutableStateOf(0L) }
        possiblyReminder?.let {
            if (remindertime == 0L) {
                val (_, time) = splitTimestamp(it.reminderDateTime)
                remindertime = time
            }
        }

        val reminderrepeat = "no-repeat"

        var showDatePicker by remember { mutableStateOf(false)}
        var showTimePicker by remember { mutableStateOf(false)}
        val mainColor = ColorObject.mainColor
        var selectedTime: TimePickerState? by remember { mutableStateOf(null) }

        Column (
            Modifier
                .padding(paddingVales)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column (
                Modifier
                    .padding(top = 200.dp, bottom = 20.dp)
                    .platformWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Row( Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

                    DateTimeButtonLabel(text = convertLongToDateString(reminderdate)) {
                        showDatePicker = true
                    }

                    DateTimeButtonLabel(text = convertLongToTimeString(remindertime)) {
                        showTimePicker = true
                    }
                }

                if (showTimePicker) {
                    TimePickerDialog(
                        onDismiss = { showTimePicker = false },
                        onConfirm = { timePickerState ->
                            // Convert to Long ---------->
                            remindertime = convertTimePickerStateToLong(timePickerState.hour, timePickerState.minute)
                            showTimePicker = false
                        },
                        textColor = mainColor
                    )
                }

                if (showDatePicker) {
                    DatePickerModalInput(
                        onDateSelected = { timestamp ->
                            if (timestamp != null) {
                                reminderdate = timestamp
                            }
                            showDatePicker = false
                        }
                    ) { showDatePicker = false }
                }

                fun editReminder(
                    reminderId: Long = reminderIdParam,
                    reminderDateTime: Long
                ) {
                    reminderRepo.update(
                        reminder = Reminder(
                            id = reminderId,
                            reminderData = itemId,
                            reminderTable = table,
                            reminderDateTime = reminderDateTime
                        )
                    )
                    showSnackbar(scope, snackbarHostState, "Lembrete actualizado com sucesso.")
                }

                submitButtonsRow {
                    CancelButton(text = "Cancelar") { navigator.pop() }

                    NormalButton("Finalizar") {
                        if (reminderdate == 0L ) {
                            showSnackbar(scope, snackbarHostState, "Por favor, selecione a data.")
                        } else if (remindertime == 0L){
                            showSnackbar(scope, snackbarHostState, "Por favor, selecione a hora.")
                        } else {
                            val reminderDateTime = combineTimestamps(reminderdate, remindertime)

                            if (reminderIdParam != 0L){
                                // Edit reminder --------->>
                                editReminder(reminderDateTime = reminderDateTime)
                            } else {
                                // Create reminder ---------->>
                                if (possiblyReminder != null) {
                                    editReminder(reminderId = possiblyReminder.id, reminderDateTime)
                                } else {
                                    reminderRepo.insert(
                                        reminder = Reminder(
                                            reminderData = itemId,
                                            reminderTable = table,
                                            reminderDateTime = reminderDateTime
                                        )
                                    )
                                    showSnackbar(scope, snackbarHostState, "Lembrete adicionado com sucesso.")
                                }
                            }

//                            scheduleReminder(
//                                title = if (table == "Song") "Lembrete de dântico" else "Lembrete de oração",
//                                message = if (table == "Song") "Eleve o seu espírito com este cântico." else "Fortaleça a sua fé com esta oração.",
//                                timestamp = reminderDateTime
//                            )

                            navigator.pop()

                        }
                    }
                }

            }
        }
    }
}

@Composable
fun DateTimeButtonLabel(text: String, onClick: () -> Unit) {
    val color = MaterialTheme.colorScheme.tertiary
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black.copy(0.3f),
            contentColor = color
        )
    ) {
        Text(text, color = color, fontSize = 32.sp, fontWeight = FontWeight.Bold)
    }
}