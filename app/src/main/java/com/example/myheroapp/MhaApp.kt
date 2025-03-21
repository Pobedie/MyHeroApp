package com.example.myheroapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myheroapp.network.Appearance
import com.example.myheroapp.network.Biography
import com.example.myheroapp.network.Connections
import com.example.myheroapp.network.HeroInfo
import com.example.myheroapp.network.Image
import com.example.myheroapp.network.PowerStats
import com.example.myheroapp.network.Work
import com.example.myheroapp.ui.screens.HeroPageScreen
import com.example.myheroapp.ui.screens.HomeScreen

enum class MhaScreens(){
    HomeScreen,
    HeroPageScreen
}


@Composable
fun MhaApp(

){
    val navController: NavHostController = rememberNavController()
    val onBackHandler = {navController.navigateUp()}
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

    NavHost(
        navController = navController,
        startDestination = MhaScreens.HomeScreen.name
    ){
        composable(MhaScreens.HomeScreen.name){
            HomeScreen(onHeroCardClick = {navController.navigate(MhaScreens.HeroPageScreen.name)})
        }
        composable(MhaScreens.HeroPageScreen.name){
            HeroPageScreen(heroInfo = heroInfo)
        }

    }
}