package com.example.myheroapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myheroapp.R
import com.example.myheroapp.network.Appearance
import com.example.myheroapp.network.Biography
import com.example.myheroapp.network.Connections
import com.example.myheroapp.network.HeroInfo
import com.example.myheroapp.network.PowerStats
import com.example.myheroapp.network.Work
import com.example.myheroapp.ui.components.ErrorScreen
import com.example.myheroapp.ui.components.FavoritesFilterItem
import com.example.myheroapp.ui.components.HeroItem
import com.example.myheroapp.ui.components.LoadingScreen
import com.example.myheroapp.ui.components.Picker
import com.example.myheroapp.ui.components.PublisherItem
import com.example.myheroapp.ui.components.TopBar
import com.example.myheroapp.utils.getPublisherImg
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    onHeroCardClick: (heroId: String) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
){
    val uiState = viewModel.uiState.collectAsState()
    val superheroApiState = viewModel.superheroApiState
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
                    updateSearchIsActive = {viewModel.changeSearchIsActive()},
                    showingFromPublisher = uiState.value.filterByPublisher,
                    publishersList = uiState.value.publishersList,
                    onSearchQuery = {viewModel.selectPublishers(publisher = it)},
                    onPublisherClick = {
                        viewModel.updateFilterByPublisher(it)
                        viewModel.changeSearchIsActive()
                                       },
                    onFavoritesClick = {
                        viewModel.changeShowOnlyFavorites()
                                       },
                    isFavoriteFilterOn = uiState.value.showOnlyFavorites
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                    ,
                    contentAlignment = Alignment.TopCenter
                ){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()

                    ) {
                        when (superheroApiState) {
                            is SuperheroApiState.Loading -> item {LoadingScreen()}
                            is SuperheroApiState.Error -> item {ErrorScreen(onRetry = {viewModel.getHeroesInfo()})}
                            is SuperheroApiState.Success -> {
                                items(superheroApiState.result){ item ->
                                    HeroItem(
                                        heroInfo = item,
                                        publisherImg = viewModel.publisherImage(item.publisher),
                                        onHeroCardClick = {onHeroCardClick(item.id)}
                                    )
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