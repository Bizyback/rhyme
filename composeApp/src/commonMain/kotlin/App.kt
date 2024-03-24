import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import ui.app.initKoin
import ui.navigation.MainNavigation

@Composable
fun App() {
    initKoin()
    MaterialTheme(
        colorScheme = ui.theme.lightColorScheme
    ) {
        MainNavigation()
    }
}