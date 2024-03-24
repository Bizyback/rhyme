package ui.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ui.screens.titles.TitlesScreen

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
@Composable
fun MainNavigation() {
    Navigator(TitlesScreen)
}