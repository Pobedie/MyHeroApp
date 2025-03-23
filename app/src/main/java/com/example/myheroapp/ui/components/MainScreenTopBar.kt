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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myheroapp.R

private const val TAG = "MainScreenTopBar"

@Composable
fun TopBar(
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



@Preview
@Composable
private fun TopBarPreview(){
    TopBar()
}
