package com.example.myheroapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
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
import dagger.hilt.android.scopes.ViewModelScoped


@Composable
fun HeroPageScreen(
    heroId: String,
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
                    .data(uiState.value.heroInfo.imageUrl)
                    .crossfade(true)
                    .build()
                ,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_broken_image),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                .background(colorResource(R.color.heropage_bio_bg))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BioTopBar(
                        heroInfo = uiState.value.heroInfo,
                        publisherImg = viewModel.publisherImage()
                    )
                    BioText(heroInfo = uiState.value.heroInfo)
                }
                BioFooter(
                    isFavorite = uiState.value.heroInfo.isFavorite,
                    onFavoriteClick = {
                        viewModel.updateIsFavorite()
                    }
                )
            }
        }
    }
}

@Composable
private fun BioTopBar(
    heroInfo: HeroInfo,
    publisherImg: Int
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
        ){
            Row(modifier = Modifier
                .padding(start = 32.dp, top = 12.dp)
                .height(40.dp)) {
                Text(
                    text = heroInfo.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(modifier = Modifier
                .padding(start = 32.dp)
                .height(50.dp)) {
                Text(
                    text = heroInfo.fullName,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
//        Publisher img and name
        Column(
            modifier = Modifier
                .padding()
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                ,
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(55.dp)
                        .width(55.dp)
                        .clip(CircleShape)
                        .background(colorResource(R.color.heropage_picture_notloaded))
                ){
                    Image(painter = painterResource(publisherImg), contentDescription = null)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 2.dp)
                ,
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = heroInfo.publisher,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun BioText(
    heroInfo: HeroInfo
){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 8.dp)
    ) {
        LazyColumn {
            item {
                Text(
                    text = stringResource(id = R.string.bio_appearance),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.bio_gender)+": " + heroInfo.appearance.gender,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_race)+": " + heroInfo.appearance.race,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_height)+": " + heroInfo.appearance.height[1],
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_eye_color)+": " + heroInfo.appearance.eyeColor,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_hair_color)+": " + heroInfo.appearance.hairColor,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.bio_biography),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.bio_place_of_birth)+": " + heroInfo.biography.placeOfBirth,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_first_appearance)+": " + heroInfo.biography.firstAppearance,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_alignment)+": " + heroInfo.biography.alignment,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_occupation)+": " + heroInfo.work.occupation,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_base)+": " + heroInfo.work.base,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_affiliation)+": " + heroInfo.connections.groupAffiliation,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_relatives)+": " + heroInfo.connections.relatives,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
private fun BioFooter(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0, 0, 0, 0),
                            colorResource(R.color.heropage_bio_bg)
                        ),
                        endY = 280f
                    )
                )
        ){
            Row(
                modifier = Modifier
                    .padding(bottom = 40.dp, end = 24.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                    ,
                    contentAlignment = Alignment.Center
                ){
                    // Shadow
                    Box(
                        modifier = Modifier
                            .offset(0.dp, 10.dp)
                            .blur(12.dp)
                    ){
                        Box(modifier = Modifier
                            .height(65.dp)
                            .width(65.dp)
                            .scale(0.65f)
                            .clip(CircleShape)
                            .background(colorResource(R.color.favorite_button_shadow))
                        )
                    }
                    Button(
                        onClick = { onFavoriteClick() },
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.favorite_button)
                        ),
                        modifier = Modifier
                            .height(55.dp)
                            .width(55.dp)
                    ) {
                        if (isFavorite){
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = stringResource(R.string.remove_favorites)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = stringResource(R.string.add_favorite)
                            )
                        }
                    }
                }
            }
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
    HeroPageScreen(heroId = "1")
}