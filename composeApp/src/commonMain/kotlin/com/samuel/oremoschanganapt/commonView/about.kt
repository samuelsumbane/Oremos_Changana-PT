package com.samuel.oremoschanganapt.commonView


import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import com.samuel.oremoschanganapt.AditionalVerticalScroll
import com.samuel.oremoschanganapt.globalComponents.showSnackbar
import com.samuel.oremoschanganapt.globalComponents.textFontSize
import com.samuel.oremoschanganapt.repository.isAndroid
import com.samuel.oremoschanganapt.repository.isDesktop
import com.samuel.oremoschanganapt.ui.theme.Blue
import oremoschangana.composeapp.generated.resources.Res
import oremoschangana.composeapp.generated.resources.arrow_back
import oremoschangana.composeapp.generated.resources.copied_text
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

const val githubLink = "https://github.com/samuel/OremosChanganaPortugues.git"

@Composable
fun NormalText(text: String, modifier: Modifier = Modifier) {
    val textColor = MaterialTheme.colorScheme.tertiary
    Text(
        text = text,
        fontSize = textFontSize(),
        color = textColor,
        textAlign = TextAlign.Justify,
        modifier = modifier.padding(17.dp, 0.dp, 17.dp, 0.dp),
    )
}

@Composable
fun SubTitleText(text: String, modifier: Modifier = Modifier) {
    val textColor = MaterialTheme.colorScheme.tertiary

    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = textColor,
        modifier = Modifier.padding(bottom = 10.dp)
            .then(modifier)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About(
    navigator: Navigator,
    onGithubClickedLink: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text = "Sobre o app", color = MaterialTheme.colorScheme.tertiary)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick={ navigator.pop() } ){
                        Icon(painterResource(Res.drawable.arrow_back), contentDescription = null)
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingVales ->
        val scrollState = rememberScrollState()
        val textColor = MaterialTheme.colorScheme.tertiary
//        val localClipboard = LocalClipboard.current
        val clipboardManager = LocalClipboardManager.current
//        val context = LocalContext.current
        val snackbarHostState = remember { SnackbarHostState() }
        val copiedTextMessage = stringResource(Res.string.copied_text)

        Box(
            modifier = Modifier
                .padding(paddingVales)
                .fillMaxSize(),
        ) {

            Column(
                modifier = Modifier
//                    .background(Color.Blue)
                    .align(Alignment.Center)
                    .fillMaxWidth(if (isDesktop()) 0.5f else 1f)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Oremos Changana - Português(PT)", color = textColor, modifier = Modifier.padding(top = 36.dp), fontSize = textFontSize(), fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(30.dp))

                NormalText("Oremos Changana - Português(PT) é um aplicativo que disponibiliza, de forma simples, intuitiva e gratuita o conteúdo do Oremos físico e um pouco mais.\n Estamos abertos para quem deseja ajudar, sua ajuda será apreciada.")

                Spacer(modifier = Modifier.height(30.dp))

                SubTitleText("Apoio")
                Text(
                    text = "A produção deste aplicativo acarretou de alguns custos da parte do programador, neste sentido pretende-se continuar a produzir mais aplicativos desta natureza e para tal contamos com o seu apoio financeiro que pode ser efetuado através dos seguintes serviços:",
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(17.dp, 0.dp, 17.dp, 0.dp),
                    fontSize = textFontSize(), lineHeight = 24.sp,
                    color = textColor
                )

                Column(
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    for((key, contact) in mutableMapOf(
                        "emola" to "865230661", "mkhesh" to "833597867",
                        "mbim" to "1046225220", "paypal" to "samuel143@gmail.com"
                    )) {
                        TextButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(contact))
                                showSnackbar(coroutineScope, snackbarHostState, copiedTextMessage)
                            },
                            modifier = Modifier.height(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Row {
                                when(key) {
                                    "emola" -> NormalText("Emola   -")
                                    "mkhesh" -> NormalText("Mkhesh   -")
                                    "mbim" -> NormalText("MBIM   -")
                                    else -> NormalText("Paypal    -")
                                }
                                LinkText(contact)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))

                SubTitleText("Programador")
                NormalText("Samuel Eugénio Sumbane")

                Spacer(modifier = Modifier.height(60.dp))

                SubTitleText("Contacto")

                Column(
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    mutableListOf(
                        "+258 865230661", "+258 833597867", "samuel143@gmail.com"
                    ).forEach { contact ->
                        TextButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(contact))
                                showSnackbar(coroutineScope, snackbarHostState, copiedTextMessage)
                            },
                            modifier = Modifier.height(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            LinkText(contact)
                        }
                    }
                }

                if (isAndroid()) {
                    Spacer(modifier = Modifier.height(35.dp))

                    SubTitleText("Agradecimentos")

                    Column(Modifier.fillMaxWidth(0.9f)) {
                        NormalText("Profundos agradecimentos para:")
                        NormalText("- Arcelia Sitoe")
                        NormalText("- Berílio Mate")
                        NormalText("- Mário Langa")
                        NormalText("- Yunura da Conceição")
                        NormalText("- Zulmira Congolo")
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))
                SubTitleText("Contribuição no código \n(Programadores ou Designers)")

                NormalText(
                    "O app Oremos Changana-PT é um projecto de código aberto disponível no GitHub e é feito 100% em Kotlin. Pode contribuir através do link:",
                )

                TextButton(
                    onClick = { onGithubClickedLink() }
                ) {
                    LinkText(githubLink)
                }

                Spacer(modifier = Modifier.height(50.dp))

                NormalText("Edição do Oremos físico: 5", modifier = Modifier.padding(bottom = 20.dp, top = 45.dp))

                Spacer(modifier = Modifier.height(20.dp))
                NormalText("Versão do aplicativo: 5.1.1", modifier = Modifier.padding(bottom = 20.dp))

            }

            if (isDesktop()) {
                Column(
                    Modifier.fillMaxHeight()
                        .width(12.dp)
                        .align(Alignment.CenterEnd)

                ) {
                    AditionalVerticalScroll(
                        modifier = Modifier,
                        lazyListState = null, scrollState = scrollState)
                }
            }
        }
    }
}

@Composable
fun LinkText(text: String) {
    Text(
        text = text,
        fontSize = textFontSize(),
        color = Blue,
        textDecoration = TextDecoration.Underline,
    )
}