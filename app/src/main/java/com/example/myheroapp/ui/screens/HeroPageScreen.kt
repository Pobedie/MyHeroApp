package com.example.myheroapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.myheroapp.network.Image
import com.example.myheroapp.network.PowerStats
import com.example.myheroapp.network.Work
import com.example.myheroapp.ui.components.BiographyCard

private const val TAG = "HeroPageScreen"

@Composable
fun HeroPageScreen(
    heroId: String,
    onBackClick: () -> Unit,
    viewModel: HeroPageScreenViewModel = hiltViewModel<HeroPageScreenViewModel>()
){
    val uiState = viewModel.uiState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .background(colorResource(R.color.heropage_picture_notloaded))
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uiState.value.heroEntity.image_url)
                    .crossfade(true)
                    .build()
                ,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_broken_image),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .padding(start = 32.dp)
                    .width(50.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 60.dp)
                        .width(50.dp)
                        .height(50.dp)
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.back_button))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.go_back))
                }

            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BiographyCard(
                heroEntity = uiState.value.heroEntity,
                publisherImg = viewModel.publisherImage(),
                updateIsFavorite = {viewModel.updateIsFavorite()})
        }
    }
}


@Preview
@Composable
private fun HeroPageScreenPreview(){
    val heroInfo = HeroInfo(
        response = "",
        id = "",
        name = "Bond",
        powerStats = PowerStats("","","","","",""),
        appearance = Appearance("male","Human", listOf("","180cm"), listOf(),"Blue","Brown"),
        biography = Biography(fullName = "James Bond with a very long name","",
            listOf(),"Casino","Casino", publisher = "EON Productions something","good"),
        work = Work("spy","everywhere"),
        connections = Connections("Bonds Bonds Bonds","Bonds from other films"),
        image = Image("")
    )
    HeroPageScreen(heroId = "1", onBackClick = {})
}