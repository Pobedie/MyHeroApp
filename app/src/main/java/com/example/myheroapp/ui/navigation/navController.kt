package com.example.myheroapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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
import com.example.sqldelight.db.HeroDatabase
import kotlinx.serialization.Serializable


enum class MhaScreens(){
    HomeScreen,
    HeroPageScreen
}

@Serializable
data object HomeScreenRoute

@Serializable
data class HeroPageRoute(
    val heroId: String
)

@Composable
fun MhaAppNavController(

){
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute
    ){
        composable<HomeScreenRoute>{
            HomeScreen(
                onHeroCardClick = { heroId ->
                    navController.navigate(HeroPageRoute(heroId = heroId)) },
            )
        }
        composable<HeroPageRoute>{
            val arguments = it.toRoute<HeroPageRoute>()
            HeroPageScreen(
                heroId = arguments.heroId,
                onBackClick = {navController.navigateUp()}
            )

        }
    }
}
