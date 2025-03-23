package com.example.myheroapp.ui.components

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
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myheroapp.R
import db.HeroEntity

private const val TAG = "BiographyCard"

@Composable
fun BiographyCard(
    heroEntity: HeroEntity,
    publisherImg: Int,
    updateIsFavorite: () -> Unit
){

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
        .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
        .background(colorResource(R.color.heropage_bio_bg))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                heroEntity = heroEntity,
                publisherImg = publisherImg
            )
            BioText(
                heroEntity = heroEntity,
            )
        }
        BioFooter(
            isFavorite = heroEntity.is_favorite==1L,
            onFavoriteClick = {
                updateIsFavorite()
            }
        )
    }
}

@Composable
private fun TopBar(
    heroEntity: HeroEntity,
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
                    text = heroEntity.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(modifier = Modifier
                .padding(start = 32.dp)
                .height(50.dp)) {
                Text(
                    text = heroEntity.full_name ?: "",
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
                    text = heroEntity.publisher,
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
    heroEntity: HeroEntity
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
                    text = stringResource(R.string.bio_gender) +": " + heroEntity.gender,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_race) +": " + heroEntity.race,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_height) +": " + heroEntity.height,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_eye_color) +": " + heroEntity.eyeColor,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_hair_color) +": " + heroEntity.hairColor,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.bio_biography),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.bio_place_of_birth) +": " + heroEntity.placeOfBirth,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_first_appearance) +": " + heroEntity.firstAppearance,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_alignment) +": " + heroEntity.alignment,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_occupation) +": " + heroEntity.occupation,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_base) +": " + heroEntity.base,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_affiliation) +": " + heroEntity.group_affiliation,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.bio_relatives) +": " + heroEntity.relatives,
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
