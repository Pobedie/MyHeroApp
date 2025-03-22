package com.example.myheroapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.myheroapp.ui.components.LoadingScreen


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
                Picker()
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

@Composable
private fun TopBar(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(colorResource(R.color.topbar_bg))
    ){
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxSize()
        ) {
            //Profile picture element
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    // Shadow
                    Box(
                        modifier = Modifier
                            .offset(0.dp, 10.dp)
                            .blur(12.dp)
                    ){
                        Box(modifier = Modifier
                            .height(62.dp)
                            .width(62.dp)
                            .scale(0.65f)
                            .clip(CircleShape)
                            .background(colorResource(R.color.ava_shadow))
                        )

                    }
                    // Profile picture
                    Box(
                        modifier = modifier
                            .height(52.dp)
                            .width(52.dp)
                            .clip(CircleShape)
                            .border(2.dp, colorResource(R.color.ava_border), CircleShape)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.ava),
                            contentDescription = null)
                    }
                }
            }

            // Greeting and username
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
            ) {
                Row(
                    modifier = Modifier
                        .height(55.dp)
                    ,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = stringResource(id = R.string.greeting),
                        style = MaterialTheme.typography.labelLarge,
                        color = colorResource(R.color.greeting_label)
                    )
                }
                Row(
                    modifier = Modifier
                        .height(35.dp)
                    ,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = stringResource(id = R.string.user_name),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }

            //Settings button
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    contentAlignment = Alignment.CenterEnd
                ){
                    Box(
                        modifier = Modifier
                            .padding(top = 8.dp, end = 26.dp)
                            .width(48.dp)
                            .height(48.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, colorResource(R.color.settings_border), CircleShape)
                            .clickable(onClick = {})
                        ,
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            tint = colorResource(R.color.setting_icon),
                            contentDescription = stringResource(id = R.string.settings),
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Picker(
    modifier: Modifier = Modifier
){
    val searchIsActive = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        SearchBar(
            query = "",
            onQueryChange = {},
            onSearch = {},
            active = searchIsActive.value,
            onActiveChange = {searchIsActive.value = !searchIsActive.value},
            placeholder = { Text(text = stringResource(id = R.string.search_placeholder))},
            trailingIcon = { Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "expand")},
            colors = SearchBarDefaults.colors(containerColor = colorResource(R.color.searchbar_bg)),
        ) {

        }

    }
}

@Composable
private fun HeroItem(
    heroInfo: HeroInfo,
    onHeroCardClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
        ,
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(0.dp, 10.dp)
                .blur(14.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(150.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(colorResource(R.color.heroitem_shadow))
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(150.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(colorResource(R.color.heroitem_container))
                .border(1.5.dp, colorResource(R.color.heroitem_border), RoundedCornerShape(24.dp))
                .clickable(onClick = onHeroCardClick)
        ){
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // Hero Picture
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(120.dp)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .height(120.dp)
                            .width(90.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(colorResource(R.color.heropicture_notloaded))
                    ){
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(heroInfo.imageUrl)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id = R.drawable.ic_broken_image),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = null,
                        )
                    }
                }

                // Hero name
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(155.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                        ,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = heroInfo.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                        ,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = heroInfo.fullName,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }

                // Publisher
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                        ,
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .height(55.dp)
                                .width(55.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(top = 8.dp)
                        ,
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Center
                    ) {
                       Text(
                           text = heroInfo.publisher,
                           style = MaterialTheme.typography.labelSmall,
                           textAlign = TextAlign.Center,
                           modifier = Modifier.width(75.dp)
                       )
                    }
                }
            }
        }
    }
}



@Preview
@Composable
private fun TopBarPreview(){
    TopBar()
}

@Preview
@Composable
private fun PickerPreview(){
    Picker()
}

@Preview
@Composable
private fun HeroItemPreview(){
    val heroInfo = HeroInfo(
        response = "",
        id = "",
        name = "Bond",
        powerStats = PowerStats("","","","","",""),
        appearance = Appearance("male","Human", listOf("","180cm"), listOf(),"Blue","Brown"),
        biography = Biography(fullName = "James Bond with a very long name","",
            listOf(),"Casino","", publisher = "EON Productions something","good"),
        work = Work("spy","everywhere"),
        connections = Connections("Bonds Bonds Bonds","Bonds from other films"),
        image = com.example.myheroapp.network.Image("")
    )
    HeroItem(
        heroInfo = heroInfo,
        onHeroCardClick = {}
    )
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview(){
    HomeScreen(onHeroCardClick = {})
}