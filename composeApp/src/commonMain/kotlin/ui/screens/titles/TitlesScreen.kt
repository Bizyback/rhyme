package ui.screens.titles

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import data.domain.TitleDomain
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import ui.extensions.shimmer
import ui.helpers.FetchItemState
import ui.screens.title.TitleScreen
import ui.theme.getRandomPastelColor

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
object TitlesScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel: TitlesScreenModel = getScreenModel()
        val state by screenModel.state.collectAsState()
        TitlesScreenContent(
            state = state,
            onClickRetryTitles = screenModel::onClickRetryClicked,
            onClickTitle = { header, pastel ->
                navigator?.push(TitleScreen(header = header, color = pastel))
            },
            onClickGetRandomTitle = screenModel::onClickGetRandomTitle
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitlesScreenContent(
    state: TitlesScreenState,
    onClickRetryTitles: () -> Unit,
    onClickTitle: (String, Int) -> Unit,
    onClickGetRandomTitle: () -> Unit
) {

    LaunchedEffect(state.title) {
        if (state.title != null) onClickTitle(state.title.first, state.title.second)
    }

    val hazeState = remember { HazeState() }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "rhyme", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
                modifier = Modifier
                    .hazeChild(
                        state = hazeState,
                        style = HazeDefaults.style(
                            tint = Color.White.copy(alpha = 0.15f),
                            blurRadius = 12.dp,
                            noiseFactor = 0.1f,
                        ),
                    )
                    .fillMaxWidth(),
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = state.titles is FetchItemState.Success
            ) {
                FloatingActionButton(
                    onClick = onClickGetRandomTitle,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(imageVector = Icons.Rounded.Explore, contentDescription = "randomize")
                }
            }
        }

    ) {
        Column(modifier = Modifier.padding(top = 40.dp).fillMaxSize()) {
            when (val result = state.titles) {
                is FetchItemState.Error -> {
                    ErrorContent(onClickRetryTitles = onClickRetryTitles)
                }

                FetchItemState.Loading -> {
                    LoadingContent(hazeState = hazeState)
                }

                is FetchItemState.Success -> {
                    SuccessContent(result.data, onClickTitle = onClickTitle, hazeState = hazeState)
                }
            }
        }
    }

}

@Composable
private fun SuccessContent(
    data: List<TitleDomain>,
    onClickTitle: (String, Int) -> Unit,
    hazeState: HazeState
) {
    val chunked = data.chunked(2)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .haze(
                state = hazeState,
                style = HazeDefaults.style(backgroundColor = MaterialTheme.colorScheme.surface),
            ),

        ) {
        items(data.size) { index ->
            val title = data[index]
            val isFirstRow = chunked.first().contains(title)
            val isLastRow = chunked.last().contains(title)
            val pastel = getRandomPastelColor().copy(alpha = 0.4f)
            Column(
                modifier = Modifier
                    .padding(top = if (isFirstRow) 100.dp else 0.dp)
                    .padding(bottom = if (isLastRow) 64.dp else 0.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(5))
                    .background(pastel)
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { onClickTitle(title.header, pastel.toArgb()) }
            ) {
                Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth(),
                        text = title.header.trim(),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorContent(onClickRetryTitles: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.padding(24.dp),
            imageVector = Icons.Rounded.Warning,
            contentDescription = ""
        )
        Text(text = "Error")
        Text(modifier = Modifier.padding(vertical = 8.dp), text = "Error")
        Button(onClick = onClickRetryTitles) {
            Text(text = "retry")
        }
    }
}

@Composable
private fun LoadingContent(hazeState: HazeState) {
    LazyVerticalGrid(
        modifier = Modifier.padding(top = 100.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(5) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(5))
                    .height(200.dp)
                    .shimmer(
                        durationMillis = 1500,
                        colors = listOf(
                            Color.Gray.copy(alpha = 0.1f),
                            Color.Gray.copy(alpha = 0.2f),
                            Color.Gray.copy(alpha = 0.3f)
                        )
                    )
                    .haze(
                        state = hazeState,
                        style = HazeDefaults.style(backgroundColor = MaterialTheme.colorScheme.surface),
                    )
            )
        }
    }
}