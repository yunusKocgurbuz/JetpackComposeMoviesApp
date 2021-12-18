package com.yunuskocgurbuz.jetpackcomposemoviesapp.view

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.util.lerp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import com.yunuskocgurbuz.jetpackcomposemoviesapp.model.movieslistmodel.ResultMovie
import com.yunuskocgurbuz.jetpackcomposemoviesapp.viewmodel.NowplayingListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@ExperimentalPagerApi
@Composable
fun ViewPagerSlider(
    navController: NavController,
    viewModel: NowplayingListViewModel = hiltViewModel()
) {

    val moviesList by remember { viewModel.moviesList }

    val nowplayingMovies: ArrayList<ResultMovie> = ArrayList<ResultMovie>()

    moviesList.forEach { movies ->
        nowplayingMovies.add(movies)
    }

    if (nowplayingMovies.size > 0) {
        val pagerState = rememberPagerState(
            pageCount = nowplayingMovies.size,
            initialPage = 2
        )

        LaunchedEffect(Unit) {
            while (true) {
                yield()
                delay(2000)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                    animationSpec = tween(600)
                )
            }
        }

        Column(
            modifier = Modifier
                .size(500.dp, 300.dp)

        ) {

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
            ) { page ->

                val movie = nowplayingMovies[page]
                Card(modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale

                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                    }
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(
                            "movie_detail_screen/${movie.id}"
                        )
                    }
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .align(Alignment.Center)
                    ) {
                        Image(
                            painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500" + movie.poster_path),
                            contentDescription = "Image",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize()
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(15.dp)
                        ) {

                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.h5,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            var movieOverview: String =
                                movie.overview.subSequence(0, 100).toString() + "..."
                            Text(
                                text = movieOverview,
                                style = MaterialTheme.typography.body1,
                                color = Color.White,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
                            )
                        }
                    }
                }
            }
            //Horizontal dot indicator
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(3.dp)
            )
        }
    }
}