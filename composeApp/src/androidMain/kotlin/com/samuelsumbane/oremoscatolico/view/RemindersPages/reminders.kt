package com.samuelsumbane.oremoscatolico.view.RemindersPages

//import Reminder
//import ReminderRepository
//import android.os.Build
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuelsumbane.oremoscatolico.ConfigureReminderScreen
import com.samuelsumbane.oremoscatolico.Reminder
import com.samuelsumbane.oremoscatolico.ReminderRepository
import com.samuelsumbane.oremoscatolico.data.praysData
import com.samuelsumbane.oremoscatolico.db.data.songsData
import com.samuelsumbane.oremoscatolico.globalComponents.ReminderButton
import com.samuelsumbane.oremoscatolico.globalComponents.TextIconRow
import com.samuelsumbane.oremoscatolico.globalComponents.platformWidth
import com.samuelsumbane.oremoscatolico.repository.convertLongToDateString
import com.samuelsumbane.oremoscatolico.repository.convertLongToTimeString
import com.samuelsumbane.oremoscatolico.repository.splitTimestamp
import oremoscatolico.composeapp.generated.resources.Res
import oremoscatolico.composeapp.generated.resources.arrow_back
import oremoscatolico.composeapp.generated.resources.help_24
import org.jetbrains.compose.resources.painterResource

//@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersPage(navigator: Navigator) {
//    val context = LocalContext.current
//    val repo = ReminderRepository(context)
    val repository = ReminderRepository()


    var showModal by remember { mutableStateOf(false) }

    var allReminders by remember { mutableStateOf(listOf<Reminder>()) }
    allReminders = repository.getAll()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text="Lembretes", color = MaterialTheme.colorScheme.tertiary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick={  navigator.pop()  } ){
                        Icon(painterResource(Res.drawable.arrow_back), contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showModal = true }) { HelpIcon() }
                }
            )
        }) { paddingValues ->

        val mainColor = ColorObject.mainColor
        val textColor = Color.White
//        val context = LocalContext.current

        AnimatedVisibility(showModal) {
//            OkAlertDialog(
//                onDismissRequest = { showModal = false },
//                onConfirmation = { showModal = false },
//                dialogTitle = "Como adicionar lembrete",
//                dialogText = " 1. Navegar até o cântico ou oração;\n 2. Clicar no icone de 3 pontinhos no canto superior direito;\n 3. Clicar no botão 'Lembrete';\n 4. Na nova janela (Definir lembrete);\n 5. Selecionar data e hora;\n 6. Finalizar.",
//                icon = ImageVector(R.drawable.help_24)
//            )
        }

        if (allReminders.isEmpty()) {
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Nenhum lembrete encontrado.", color = textColor, fontWeight = FontWeight.SemiBold)
            }
        } else {

            LazyColumn(
                Modifier.fillMaxSize().padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(allReminders) { reminder ->
                    fun seeReminderContent(){
                        if (reminder.reminderTable == "Pray"){
//                            navigator.navigate("eachOracao/${reminder.reminderData}")
//                            navigator.push(Each)
                        } else {
//                            navigator.navigate("eachCantico/${reminder.reminderData}")
                        }
                    }

                    val (reminderdate, remindertime) = splitTimestamp(reminder.reminderDateTime)

                    val reminderTitleValue by remember { mutableStateOf(if (reminder.reminderTable == "Pray") {
                        "Oração: ${ praysData.firstOrNull { it.id == reminder.reminderData }?.title ?: "" }"
                    } else {
                        songsData.firstOrNull { it.id == reminder.reminderData }?.run {
                            "Cântco: $number - $title"
                        } ?: ""
                    }) }

                    var reminderTitle by remember { mutableStateOf(reminderTitleValue) }
                    var reminderTitleText by remember { mutableStateOf("") }

                    Column(
                        Modifier
                            .platformWidth()
                            .background(ColorObject.mainColor, RoundedCornerShape(10.dp)),
                    ) {
                        var showDetails by remember { mutableStateOf(false) }

                        fun showContent() {
                            if (showDetails) {
                                reminderTitle = reminderTitleValue
                                reminderTitleText = ""
                            } else {
                                reminderTitle = ""
                                reminderTitleText = reminderTitleValue
                            }
                            showDetails = !showDetails
                        }

                        TextIconRow(reminderTitle, showDetails, modifier = Modifier.clickable { showContent() })

                        AnimatedVisibility(showDetails){
                            Column(
                                Modifier.fillMaxWidth().background(brush = Brush.horizontalGradient(
                                    colors = listOf(mainColor, lerp(mainColor, ColorObject.secondColor, 0.9f)
                                    )), RoundedCornerShape(0.dp, 0.dp, 14.dp, 14.dp))
                                    .clickable { seeReminderContent() }

                            ) {
                                Row(Modifier.padding(10.dp)) {
                                    Text(reminderTitleText, color = textColor, softWrap = true)
                                }

                                Spacer(Modifier.height(15.dp))

                                Row(modifier = Modifier.padding(10.dp)) {
                                    Text("${convertLongToDateString(reminderdate)}  |", color = textColor)
                                    Text(" ${convertLongToTimeString(remindertime)}", color = textColor)
                                }

                                Spacer(Modifier.height(15.dp))

                                Row (
                                    Modifier.fillMaxWidth(0.9f)
                                        .align(Alignment.CenterHorizontally),
                                    horizontalArrangement = Arrangement.SpaceAround){

                                    ReminderButton("Ver") { seeReminderContent() }

                                    ReminderButton("Editar") {
                                        val r = reminder
//                                        navigator.navigate("configurereminder/${r.reminderData}/${r.reminderTable}/${r.id}")
                                        navigator.push(
                                            ConfigureReminderScreen(
                                                itemId = r.reminderData,
                                                table = r.reminderTable,
                                                reminderIdParam = r.id
                                            )
                                        )
                                    }

                                    ReminderButton("Remover") {
//                                        repo.deleteById(reminder.id)
                                        repository.deleteById(reminder.id)
                                        showContent()
//                                        toastAlert(context, "Lembrete removido com sucesso.")

                                        allReminders = repository.getAll()
                                    }
                                }
                                Spacer(Modifier.height(15.dp))
                            }
                        }
                    }
                    Spacer(Modifier.height(15.dp))
                }
            }
        }

//        ShortcutsButton(navigator)
    }
}


@Composable
fun HelpIcon() {
    Icon(painterResource(Res.drawable.help_24), contentDescription = "Ajuda sobre lembretes", modifier = Modifier.size(25.dp))
}