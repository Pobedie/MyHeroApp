package com.example.myheroapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import com.example.myheroapp.R
import com.example.myheroapp.ui.components.ErrorScreen
import com.example.myheroapp.ui.components.HeroItem
import com.example.myheroapp.ui.components.LoadingScreen
import com.example.myheroapp.ui.components.Picker
import com.example.myheroapp.ui.components.TopBar
import kotlinx.coroutines.coroutineScope

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    onHeroCardClick: (heroId: String) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
){
    val uiState = viewModel.uiState.collectAsState()
    val superheroApiState = viewModel.superheroApiState
    val heroList = viewModel.allHeroes.collectAsState(initial = listOf())
    val listState = rememberLazyListState(
//        TODO
    )

    DisposableEffect(key1 = Unit) {
        onDispose {
            viewModel.updateLazyListState(listState)
            println("ON DISPOSE")
        }
    }

    Scaffold(
        topBar = {TopBar(Modifier.padding(top = 8.dp))},
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(colorResource(R.color.content_bg))
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Picker(
                    searchIsActive = uiState.value.searchIsActive,
                    updateSearchIsActive = {
                        viewModel.selectPublishers("")
                        viewModel.changeSearchIsActive() },
                    showingFromPublisher = uiState.value.filterByPublisher,
                    publishersList = uiState.value.publishersList,
                    onSearchQuery = {viewModel.selectPublishers(it ?: "")},
                    onPublisherClick = {
                        viewModel.updateFilterByPublisher(it)
                        viewModel.changeSearchIsActive() },
                    onFavoritesClick = {
                        viewModel.changeShowOnlyFavorites() },
                    isFavoriteFilterOn = uiState.value.showOnlyFavorites,
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                    ,
                    contentAlignment = Alignment.TopCenter
                ){
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        when (superheroApiState) {
                            is SuperheroApiState.Loading -> item {LoadingScreen()}
                            is SuperheroApiState.Error -> item {ErrorScreen(onRetry = {viewModel.getHeroesInfo()})}
                            is SuperheroApiState.Success -> {
                                items(heroList.value){ item ->
                                    HeroItem(
                                        heroEntity = item,
                                        publisherImg = viewModel.publisherImage(item.publisher),
                                        onHeroCardClick = {onHeroCardClick(item.id)}
                                    )
                                }
                                if (!uiState.value.showOnlyFavorites && uiState.value.filterByPublisher==""){
                                    item{
                                        val buttonPressed = remember {
                                            mutableStateOf(false)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(80.dp)
                                                .clickable {
                                                    if (!buttonPressed.value) {
                                                        viewModel.loadMoreElements()
                                                        buttonPressed.value = true
                                                    }
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (buttonPressed.value){
                                                CircularProgressIndicator()
                                            } else {
                                                Text(text = "Load more")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        colorResource(R.color.content_bg),
                                        Color(0, 0, 0, 0)
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}



@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview(){
    HomeScreen(onHeroCardClick = {})
}