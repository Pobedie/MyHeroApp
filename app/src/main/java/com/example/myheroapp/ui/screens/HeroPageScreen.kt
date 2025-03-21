package com.example.myheroapp.ui.screens

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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


@Composable
fun HeroPageScreen(
    heroInfo: HeroInfo,
    viewModel: HeroPageScreenViewModel = viewModel()
){
    val uiState = viewModel.uiState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .background(Color.Gray)
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(heroInfo.imageUrl)
                    .crossfade(true)
                    .build()
                ,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_broken_image)
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
                .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BioTopBar(heroInfo = heroInfo)
                    BioText(heroInfo = heroInfo)
                }
                BioFooter(
                    isFavorite = uiState.value.isFavorite,
                    onFavoriteClick = {}
                )
            }
        }
    }
}

@Composable
private fun BioTopBar(
    heroInfo: HeroInfo
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
                        .background(Color.Gray)
                )
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
                    text = "Appearance",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Gender: " + heroInfo.appearance.gender,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "Race: " + heroInfo.appearance.race,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "Height: " + heroInfo.appearance.height[1],
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "Eye color: " + heroInfo.appearance.eyeColor,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "Hair color: " + heroInfo.appearance.hairColor,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Biography",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Place of Birth: " + heroInfo.biography.placeOfBirth,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "First Appearance: " + heroInfo.biography.firstAppearance,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "Behaviour alignment: " + heroInfo.biography.alignment,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "Occupation: " + heroInfo.work.occupation,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "Base: " + heroInfo.work.base,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "Group affiliation: " + heroInfo.connections.groupAffiliation,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
                Text(
                    text = "Relatives: " + heroInfo.connections.relatives,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
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
                            Color.White
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
                            .background(Color(255, 66, 66, 200))
                        )
                    }
                    Button(
                        onClick = { onFavoriteClick() },
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(255,66,66)
                        ),
                        modifier = Modifier
                            .height(55.dp)
                            .width(55.dp)
                    ) {
                        if (isFavorite){
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Remove from favorites"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Add to favorites"
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
            listOf(),"Casino","", publisher = "EON Productions something","good"),
        work = Work("spy","everywhere"),
        connections = Connections("Bonds Bonds Bonds","Bonds from other films"),
        image = Image("")
    )
    HeroPageScreen(heroInfo)
}