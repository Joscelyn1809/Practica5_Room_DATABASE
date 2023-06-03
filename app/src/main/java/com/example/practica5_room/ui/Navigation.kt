package com.example.practica5_room.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.practica5_room.ui.detail.DetailScreen
import com.example.practica5_room.ui.home.HomeScreen

enum class Routes {
    Home,
    DetailScreen,
    Prueba
}

@Composable
fun CountryAppNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(navController = navHostController, startDestination = Routes.Home.name) {
        composable(route = Routes.Home.name) {
            HomeScreen(onNavigate = { id ->
                navHostController.navigate(route = "${Routes.DetailScreen.name}?id=$id")
            })
        }

        composable(
            route = "${Routes.DetailScreen.name}?id=${id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: -1
            DetailScreen(id = id) {
                navHostController.navigateUp()
            }
        }

    }
}