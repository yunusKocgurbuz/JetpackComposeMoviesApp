package com.yunuskocgurbuz.jetpackcomposemoviesapp.view

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.yunuskocgurbuz.jetpackcomposemoviesapp.R
import com.yunuskocgurbuz.jetpackcomposemoviesapp.model.movieslistmodel.ResultMovie
import com.yunuskocgurbuz.jetpackcomposemoviesapp.util.ConnectionState
import com.yunuskocgurbuz.jetpackcomposemoviesapp.util.connectivityState
import com.yunuskocgurbuz.jetpackcomposemoviesapp.viewmodel.MovieDetailViewModel
import com.yunuskocgurbuz.jetpackcomposemoviesapp.viewmodel.MoviesListViewModel
import com.yunuskocgurbuz.jetpackcomposemoviesapp.viewmodel.NowplayingListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay


@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun MoviesListScreen(
    navController: NavController
) {

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            ConnectivityStatus()
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

    SwipeRefreshCompose(moviesList, navController = navController)
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

@Composable
fun ConnectivityStatusBox(
    isConnected: Boolean
) {
    val backgroundColor by animateColorAsState(targetValue = if (isConnected) Color.Green else Color.Red)
    val message = if (isConnected) "Back Online!" else "No Internet Connection!"
    val iconResource = if (isConnected) {
        R.drawable.ic_connectivity_available
    } else {
        R.drawable.ic_connectivity_unavailable
    }
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = "Connection Image",
                tint = Color.White
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = message,
                color = Color.White,
                fontSize = 15.sp
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@Composable
fun ConnectivityStatus() {
    val connection by connectivityState()
    val isConnected = connection == ConnectionState.Available
    var visibility by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        ConnectivityStatusBox(isConnected = isConnected)
    }

    LaunchedEffect(isConnected) {

        visibility = if (!isConnected) {
            true
        } else {
            delay(2000)
            false
        }
    }
}

