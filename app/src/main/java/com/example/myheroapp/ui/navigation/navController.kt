package com.example.myheroapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myheroapp.ui.screens.HeroPageScreen
import com.example.myheroapp.ui.screens.HomeScreen
import kotlinx.serialization.Serializable


private const val TAG = "navController"

@Serializable
data object HomeScreenRoute

@Serializable
data class HeroPageRoute(
    val heroId: Int
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
