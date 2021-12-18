package com.yunuskocgurbuz.jetpackcomposemoviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.yunuskocgurbuz.jetpackcomposemoviesapp.ui.theme.JetpackComposeMoviesAppTheme
import com.yunuskocgurbuz.jetpackcomposemoviesapp.view.MoviesListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeMoviesAppTheme {

                val navController = rememberNavController()

                NavHost(navController =  navController, startDestination = "movies_list_screen"){

                    composable("movies_list_screen"){
                        //NewsListScreen
                        MoviesListScreen(navController = navController)
                    }

                }

            }
        }
    }
}
