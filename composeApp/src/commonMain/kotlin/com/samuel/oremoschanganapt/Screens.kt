package com.samuel.oremoschanganapt

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import com.samuel.oremoschanganapt.commonView.About
import com.samuel.oremoschanganapt.commonView.commonMorePages.Licionario
import com.samuel.oremoschanganapt.view.morepagesPackage.Apendice
import com.samuel.oremoschanganapt.commonView.commonMorePages.FestasMoveis
import com.samuel.oremoschanganapt.commonView.commonMorePages.Santoral
import com.samuel.oremoschanganapt.commonView.settingsPages.AppearancePage

object SantoralScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Santoral(navigator)
    }
}


object ApendixScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Apendice(navigator)
    }
}

object LicionarioScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Licionario(navigator)
    }
}

//expect class CommonSideBarScreen(activePage: String) : Screen


object FestasMoveisScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        FestasMoveis(navigator)
    }
}


expect class ConfigureReminderScreen(
    itemId: Int,
    table: String,
    reminderIdParam: Long
) : Screen

expect object RemindersScreen : Screen

expect object HomeScreen : Screen

@Composable
expect fun AditionalVerticalScroll(
    modifier: Modifier,
    lazyListState: LazyListState?,
    scrollState: ScrollState?
)

expect fun createSettings(): Settings

expect class ReminderRepository() {
    fun insert(reminder: Reminder): Long
    fun getAll(): List<Reminder>
    fun deleteById(id: Long): Int
    fun update(reminder: Reminder): Int
    fun close()
}


/**
 * This component will be only for Mobile
 *
 */

@Composable
expect fun BottomNav(
    navigator: Navigator,
    activePage: String
)


object CommonAboutAppScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        About(navigator, onGithubClickedLink = {
//            val intent = Intent(Intent.ACTION_VIEW, githubLink.toUri())
//            context.startActivity(intent)
        })
    }
}

object AppearanceScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        AppearancePage(navigator)
    }
}
@Composable
expect fun searchWidget(
    searchInputLabel: String = "Pesquisar oração",
    searchValue: (String) -> Unit
)



// This function will works only on Android (maybe in IOS in future)
expect fun shareContent(text: String)

@Composable
expect fun shortcutButtonWidget(navigator: Navigator)

@Composable
expect fun isMobilePortrait(): Boolean