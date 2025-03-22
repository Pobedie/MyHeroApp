package com.example.myheroapp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
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


@Composable
fun PublisherItem(
    publisherName: String,
    publisherImg: Int,
    onClick: (String) -> Unit,
//    isSelected: Boolean,
    modifier: Modifier = Modifier
){
//    TODO()
    val targetColor = if (false) colorResource(R.color.publisheritem_selected_container) else Color.White
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 500), label = ""
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
        ,
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(0.dp, 4.dp)
                .blur(4.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(65.dp)
                    .clip(RoundedCornerShape(44.dp))
                    .background(colorResource(R.color.heroitem_shadow))
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(65.dp)
                .clip(RoundedCornerShape(44.dp))
                .background(animatedColor)
                .border(1.5.dp, colorResource(R.color.heroitem_border), RoundedCornerShape(44.dp))
                .clickable(onClick = { onClick(publisherName) })
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
//                Image
                Column(
                    modifier = Modifier
                        .padding(start = 5.dp, top = 5.dp)
                    ,
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
//                Text
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 16.dp, end = 8.dp)
                    ,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = publisherName,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}



@Preview(showSystemUi = true)
@Composable
private fun PublisherItemPreview(){
    PublisherItem(publisherName = "Marvel marvel marvel",
        publisherImg = R.drawable.marvel_comics,
        onClick = {},
//        isSelected = false
    )
}