package com.example.bookstore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookstore.screens.details.BookDetailsScreen
import com.example.bookstore.screens.home.HomeScreen
import com.example.bookstore.screens.home.HomeScreenViewModel
import com.example.bookstore.screens.login.LoginScreen
import com.example.bookstore.screens.search.BookSearchViewModel
import com.example.bookstore.screens.search.SearchScreen
import com.example.bookstore.screens.splashScreen.SplashScreen
import com.example.bookstore.screens.stats.StatsScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.name
    ) {

        composable(Screens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(Screens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }

        composable(Screens.ReaderStatsScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            StatsScreen(navController = navController, viewModel = homeViewModel)
        }
//
        composable(Screens.ReaderHomeScreen.name) {
            //val homeViewModel = hiltViewModel<HomeScreenViewModel>()
//            HomeScreen(navController = navController, viewModel = homeViewModel)
            HomeScreen(navController = navController)
        }

        composable(Screens.SearchScreen.name) {
            val searchViewModel = hiltViewModel<BookSearchViewModel>()
            SearchScreen(navController = navController,searchViewModel)
        }

        val detailName = Screens.DetailScreen.name
        composable("$detailName/{bookId}", arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(navController = navController, bookId = it.toString())
            }
        }
//
//        val updateName = Screens.UpdateScreen.name
//        composable("$updateName/{bookItemId}",
//            arguments = listOf(navArgument("bookItemId") {
//                type = NavType.StringType
//            })) { navBackStackEntry ->
//
//            navBackStackEntry.arguments?.getString("bookItemId").let {
//                BookUpdateScreen(navController = navController, bookItemId = it.toString())
//            }
//
//        }
    }
}