package ui.screens.home

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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.helpers.FetchItemState

/**
 * project : rhyme
 * date    : Sunday 24/03/2024
 * user    : mambo
 * email   : mambobryan@gmail.com
 */
object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel: HomeScreenModel = getScreenModel()
        val state by screenModel.state.collectAsState()
        HomeScreenContent(state = state, onClickRetryTitles = screenModel::onClickRetryClicked)
    }
}

@Composable
fun HomeScreenContent(
    state: HomeScreenState,
    onClickRetryTitles: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = " ryhme") }
            )
        }
    ) { values ->
        Column(modifier = Modifier.padding(values)) {
            when (val result = state.titles) {
                is FetchItemState.Error -> {
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

                FetchItemState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Loading")
                    }
                }

                is FetchItemState.Success -> {
                    val list = result.data
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(list) { title ->
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(5))
                                    .background(Color.Gray.copy(0.1f))
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clickable {  }
                            ) {
                                Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                                    Text(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .fillMaxWidth(),
                                        text = title.header,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}