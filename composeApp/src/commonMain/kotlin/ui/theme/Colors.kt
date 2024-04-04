package ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
val lightColorScheme = lightColorScheme(
    background = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF),
    primary = Color(0xFF000000),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF000000),
    onSecondary = Color(0xFFFFFFFF),
)

fun getRandomPastelColor(): Color {
    val (red, green, blue) = Triple(
        first = (0 until 128).random().plus(127),
        second = (0 until 128).random().plus(127),
        third = (0 until 128).random().plus(127)
    )
    return Color(red, green, blue)
}