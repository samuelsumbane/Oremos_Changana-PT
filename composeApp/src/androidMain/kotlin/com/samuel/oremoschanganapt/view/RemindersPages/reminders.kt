package com.samuel.oremoschanganapt.view.RemindersPages


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.repository.ColorObject
import com.samuel.oremoschanganapt.ConfigureReminderScreen
import com.samuel.oremoschanganapt.Reminder
import com.samuel.oremoschanganapt.ReminderRepository
import com.samuel.oremoschanganapt.commonView.EachPageScreen
import com.samuel.oremoschanganapt.components.OkAlertDialog
import com.samuel.oremoschanganapt.data.androidpraysList
import com.samuel.oremoschanganapt.globalComponents.ReminderButton
import com.samuel.oremoschanganapt.globalComponents.TextIconRow
import com.samuel.oremoschanganapt.globalComponents.platformWidth
import com.samuel.oremoschanganapt.globalComponents.showSnackbar
import com.samuel.oremoschanganapt.repository.DataCollection
import com.samuel.oremoschanganapt.repository.convertLongToDateString
import com.samuel.oremoschanganapt.repository.convertLongToTimeString
import com.samuel.oremoschanganapt.repository.splitTimestamp
import com.samuel.oremoschanganapt.songsList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.arrow_back
import oremoschangana.composeapp.generated.resources.help_24
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
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

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
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->

        val mainColor = ColorObject.mainColor
        val textColor = Color.White
//        val context = LocalContext.current

        AnimatedVisibility(showModal) {
            OkAlertDialog(
                onDismissRequest = { showModal = false },
                onConfirmation = { showModal = false },
                dialogTitle = "Como adicionar lembrete",
                dialogText = " 1. Navegar até o cântico ou oração;\n 2. Clicar no icone de 3 pontinhos no canto superior direito;\n 3. Clicar no botão 'Lembrete';\n 4. Na nova janela (Definir lembrete);\n 5. Selecionar data e hora;\n 6. Finalizar.",
                icon = painterResource(Res.drawable.help_24)
            )
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
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(allReminders, key = { item -> item.id}) { reminder ->
                    fun seeReminderContent(){
                        if (reminder.reminderTable == "Pray"){
                            navigator.push(EachPageScreen(dataCollection = DataCollection.PRAYS, itemId = reminder.reminderData))
                        } else {
                            navigator.push(EachPageScreen(dataCollection = DataCollection.SONGS, itemId = reminder.reminderData))
                        }
                    }

                    val (reminderdate, remindertime) = splitTimestamp(reminder.reminderDateTime)

                    val reminderTitleValue by remember { mutableStateOf(if (reminder.reminderTable == "Pray") {
                        "Oração: ${ androidpraysList.firstOrNull { it.id == reminder.reminderData }?.title ?: "" }"
                    } else {
                        songsList.firstOrNull { it.id == reminder.reminderData }?.run {
                            "Cântco: $number - $title"
                        } ?: ""
                    }) }

                    var reminderTitle by remember { mutableStateOf(reminderTitleValue) }
                    var reminderTitleText by remember { mutableStateOf("") }

                    Column(
                        Modifier
                            .padding(10.dp)
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
                                        navigator.push(
                                            ConfigureReminderScreen(
                                                itemId = r.reminderData,
                                                table = r.reminderTable,
                                                reminderIdParam = r.id
                                            )
                                        )
                                    }

                                    ReminderButton("Remover") {
                                        repository.deleteById(reminder.id)
                                        showContent()
                                        showSnackbar(coroutineScope, snackbarHostState, "Lembrete removido com sucesso.")
                                        coroutineScope.launch {
                                            delay(800)
                                            allReminders = repository.getAll()
                                        }
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