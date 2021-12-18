package com.yunuskocgurbuz.jetpackcomposemoviesapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.yunuskocgurbuz.jetpackcomposemoviesapp.model.movieslistmodel.ResultMovie
import com.yunuskocgurbuz.jetpackcomposemoviesapp.viewmodel.MoviesListViewModel
import kotlinx.coroutines.delay


@ExperimentalPagerApi
@Composable
fun MoviesListScreen(
    navController: NavController,
    viewModel: MoviesListViewModel = hiltViewModel()
) {

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {

            MoviesList(navController = navController)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun MoviesList(navController: NavController, viewModel: MoviesListViewModel = hiltViewModel()) {
    val moviesList by remember { viewModel.moviesList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    SwipeRefreshCompose(moviesList, navController = navController)


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (errorMessage.isNotEmpty()) {
            RetryView(error = errorMessage) {
                viewModel.loadMovies()
            }
        }
    }
}


@ExperimentalPagerApi
@Composable
fun SwipeRefreshCompose(movies: List<ResultMovie>, navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        var refreshing by remember { mutableStateOf(false) }
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(2000)
                refreshing = false
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refreshing),
            onRefresh = { refreshing = true }
        ) {

            LazyColumn(contentPadding = PaddingValues(0.dp)) {

                //now playing, a slider on top of the screen,
                item{
                    ViewPagerSlider(navController = navController)
                }

                //upcoming
                items(movies) { movie ->
                    MoviesRow(navController = navController, movie = movie)
                }
            }

        }
    }
}

@Composable
fun MoviesRow(navController: NavController, movie: ResultMovie) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                navController.navigate(
                    "movie_detail_screen/${movie.id}"
                )
            },
        backgroundColor = Color.LightGray,
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {

        Row{
            Image(
                painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500" + movie.poster_path),
                contentDescription = movie.title,
                modifier = Modifier
                    .size(150.dp)
                    .padding(0.dp)
                    .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 0.dp)),
                contentScale = ContentScale.FillBounds
            )

            Column() {



                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(3.dp, 20.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                var movieOverview: String = movie.overview.subSequence(0, 100).toString() + "..."
                Text(
                    text = movieOverview,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(3.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

            }
        }

    }
}


@Composable
fun RetryView(
    error: String,
    onRetry: () -> Unit
) {
    Column() {
        Text(error, color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { onRetry }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Retry")
        }
    }
}

