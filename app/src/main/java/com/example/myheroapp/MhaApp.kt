package com.example.myheroapp

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.myheroapp.network.Appearance
import com.example.myheroapp.network.Biography
import com.example.myheroapp.network.Connections
import com.example.myheroapp.network.HeroInfo
import com.example.myheroapp.network.Image
import com.example.myheroapp.network.PowerStats
import com.example.myheroapp.network.Work
import com.example.myheroapp.ui.screens.HeroPageScreen
import com.example.myheroapp.ui.screens.HomeScreen
import com.example.myheroapp.ui.screens.HomeScreenViewModel
import com.example.sqldelight.db.HeroDatabase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MhaApp : Application()