@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

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
import com.samuel.oremoschanganapt.ui_core.About
import com.samuel.oremoschanganapt.ui_core.commonMorePages.FestasMoveis
import com.samuel.oremoschanganapt.ui_core.commonMorePages.Licionario
import com.samuel.oremoschanganapt.ui_core.commonMorePages.Santoral
import com.samuel.oremoschanganapt.ui_core.globalComponents.Pray
import com.samuel.oremoschanganapt.ui_core.globalComponents.Song
import com.samuel.oremoschanganapt.ui_core.settingsPages.AppearancePage
import com.samuel.oremoschanganapt.view.morepagesPackage.Apendice

class SantoralScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Santoral(navigator)
    }
}


class ApendixScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Apendice(navigator)
    }
}

class LicionarioScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Licionario(navigator)
    }
}

//expect class CommonSideBarScreen(activePage: String) : Screen


class FestasMoveisScreen : Screen {
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

expect class RemindersScreen() : Screen

expect class HomeScreen() : Screen

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
    private fun readResolve(): Any = CommonAboutAppScreen

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        About(navigator, onGithubClickedLink = {
//            val intent = Intent(Intent.ACTION_VIEW, githubLink.toUri())
//            context.startActivity(intent)
        })
    }
}

class AppearanceScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        AppearancePage(navigator)
    }
}
@Composable
expect fun searchWidget(
    searchInputLabel: String = "Pesquisar oração",
    expanded: Boolean = false,
    onExpand: (Boolean) -> Unit,
    searchValue: (String) -> Unit
)


// This function will works only on Android (maybe in IOS in future)
expect fun shareContent(text: String)

@Composable
expect fun shortcutButtonWidget(navigator: Navigator)

@Composable
expect fun isMobilePortrait(): Boolean

@Composable
expect fun PagerContent(
    modifier: Modifier,
    navigator: Navigator,
    title: String,
    subTitle: String,
    body: String,
    showShortcutButton: Boolean = true,
)

expect val songsList: List<Song>
expect val praysList: List<Pray>