package com.example.myheroapp.ui.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myheroapp.R
import com.example.myheroapp.network.Appearance
import com.example.myheroapp.network.Biography
import com.example.myheroapp.network.Connections
import com.example.myheroapp.network.HeroInfo
import com.example.myheroapp.network.PowerStats
import com.example.myheroapp.network.Work
import com.example.myheroapp.utils.toHeroEntity
import db.HeroEntity

private const val TAG = "HeroItem"

@Composable
fun HeroItem(
    heroEntity: HeroEntity,
    publisherImg: Int,
    onHeroCardClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp)
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
                                .data(heroEntity.image_url)
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
                            .padding(top = 16.dp)
                        ,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = heroEntity.name,
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
                            text = heroEntity.full_name ?: "",
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
                        ){
                            Image(painter = painterResource(publisherImg), contentDescription = null)
                        }
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
                            text = heroEntity.publisher,
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
        heroEntity = heroInfo.toHeroEntity(),
        publisherImg = R.drawable.ic_broken_image,
        onHeroCardClick = {}
    )
}
