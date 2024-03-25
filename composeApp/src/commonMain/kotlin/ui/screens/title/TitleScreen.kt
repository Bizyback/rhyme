package ui.screens.title

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import ui.extensions.shimmer
import ui.helpers.FetchItemState
import kotlin.random.Random

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
class TitleScreen(val header: String, val color: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel: TitleScreenModel = getScreenModel()
        screenModel.updateHeader(value = header)
        val state by screenModel.state.collectAsState()
        TitleScreenContent(
            pastel = color,
            state = state,
            onClickRetry = screenModel::onClickRetry,
            onClickNavigateBack = { navigator?.pop() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleScreenContent(
    pastel: Int,
    state: TitleScreenState,
    onClickRetry: () -> Unit,
    onClickNavigateBack: () -> Unit,
) {

    val hazeState = remember { HazeState() }

    Scaffold(modifier = Modifier.background(Color(pastel)), topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onClickNavigateBack) {
                    Icon(Icons.Rounded.ArrowBack, "")
                }
            },
            title = {
                Text(modifier = Modifier.padding(vertical = 8.dp), text = state.header ?: "Title")
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
            modifier = Modifier
                .hazeChild(
                    state = hazeState,
                    style = HazeDefaults.style(
                        tint = Color.Black.copy(alpha = 0.15f),
                        blurRadius = 12.dp,
                        noiseFactor = 0.1f,
                    ),
                )
                .fillMaxWidth(),
        )
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            when (val result = state.poem) {
                is FetchItemState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.padding(24.dp).size(75.dp),
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = "warning error"
                        )
                        Text(text = "Error")
                        Text(modifier = Modifier.padding(vertical = 8.dp), text = result.message)
                        Button(onClick = onClickRetry) {
                            Text(text = "retry", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                FetchItemState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        LazyColumn(
                            modifier = Modifier.padding(top = 100.dp)
                                .haze(
                                    state = hazeState,
                                    style = HazeDefaults.style(backgroundColor = MaterialTheme.colorScheme.surface),
                                ),
                        ) {
                            items(5) {
                                val weight = Random.nextInt(10, 90).div(100f)
                                Text(
                                    text = "",
                                    modifier = Modifier.fillMaxWidth(weight)
                                        .padding(vertical = 4.dp)
                                        .shimmer(
                                            durationMillis = 1500,
                                            colors = listOf(
                                                Color.Gray.copy(alpha = 0.1f),
                                                Color.Gray.copy(alpha = 0.2f),
                                                Color.Gray.copy(alpha = 0.3f)
                                            )
                                        )

                                )
                            }
                        }

                    }
                }

                is FetchItemState.Success -> {
                    val poem = result.data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .haze(
                                state = hazeState,
                                style = HazeDefaults.style(backgroundColor = MaterialTheme.colorScheme.surface),
                            ),
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp)
                                .padding(top = 140.dp, bottom = 16.dp),
                            text = "By ${poem.author}",
                            fontSize = TextUnit(16f, TextUnitType.Sp),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 40.dp),
                            text = poem.lines.take(poem.count)
                                .joinToString(separator = "") { "$it\n" },
                        )
                    }
                }
            }
        }
    }

}
