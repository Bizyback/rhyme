import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.app.initKoin
import ui.navigation.MainNavigation

@Composable
fun App() {
    initKoin()
    MaterialTheme {
        MainNavigation()
    }
}