package com.example.myheroapp.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myheroapp.R

private const val TAG = "FavoritesFilterItem"

@Composable
fun FavoritesFilterItem(
    onClick: () -> Unit,
    isFilterOn: Boolean,
    modifier: Modifier = Modifier
){
    val targetColor = if (isFilterOn) colorResource(R.color.filterbyfavorites_button) else Color.White
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 500), label = "" // 1-second duration
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
        ,
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(0.dp, 9.dp)
                .blur(7.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .height(65.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(R.color.heroitem_shadow))
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .height(65.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    animatedColor
                )
                .clickable(onClick = { onClick() })
        ){
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(18.dp))
                Text(
                    text = stringResource(R.string.only_favorites),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun FavoritesFilterItemPreview(){
    FavoritesFilterItem(onClick = { /*TODO*/ },isFilterOn = true)
}