package com.example.weatherbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherbuddy.ui.mainScreen.MainScreen
import com.example.weatherbuddy.ui.splashScreen.SplashScreen

@Composable
fun SplashNavigation()
{
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Splash" )
    {
        composable("Splash")
        {
            SplashScreen( changeToMain = {
                navController.navigate("Main"){
                popUpTo("Splash"){
                    inclusive = true
                }
            }
            })
        }
        composable("Main")
        {
            MainScreen()
        }
    }
}