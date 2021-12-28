package com.yunuskocgurbuz.jetpackcomposemoviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.yunuskocgurbuz.jetpackcomposemoviesapp.ui.theme.JetpackComposeMoviesAppTheme
import com.yunuskocgurbuz.jetpackcomposemoviesapp.view.MovieDetailScreen
import com.yunuskocgurbuz.jetpackcomposemoviesapp.view.MoviesListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeMoviesAppTheme {

                val navController = rememberNavController()

                NavHost(navController =  navController, startDestination = "movies_list_screen"){

                    composable("movies_list_screen"){

                        MoviesListScreen(navController = navController)
                    }

                    composable("movie_detail_screen/{movie_id}", arguments = listOf(
                        navArgument("movie_id"){
                            type = NavType.StringType
                        }
                    )){
                        val movie_id = remember {
                            it.arguments?.getString("movie_id")
                        }

                        MovieDetailScreen(id = movie_id ?: "")
                    }

                }

            }
        }
    }
}
