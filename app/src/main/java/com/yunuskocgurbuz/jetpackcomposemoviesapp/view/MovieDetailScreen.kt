package com.yunuskocgurbuz.jetpackcomposemoviesapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.yunuskocgurbuz.jetpackcomposemoviesapp.model.moviesdetailmodel.MoviesDetailList
import com.yunuskocgurbuz.jetpackcomposemoviesapp.util.Resource
import com.yunuskocgurbuz.jetpackcomposemoviesapp.viewmodel.MovieDetailViewModel

@Composable
fun MovieDetailScreen(
    id: String,
    viewModel: MovieDetailViewModel = hiltViewModel()
){

    val movieDetail by produceState<Resource<MoviesDetailList>>(initialValue = Resource.Loading()){
        value = viewModel.getMovieDetail(id)

    }

    MovieDetail(movieDetail = movieDetail)

}


@Composable
fun MovieDetail(movieDetail: Resource<MoviesDetailList>){

    LazyColumn() {
        item{

            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
                contentAlignment = Alignment.Center,
            ) {
                Column {
                    when(movieDetail) {

                        is Resource.Success -> {

                            val selectedMovie = movieDetail.data!!

                            Image(painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500" + selectedMovie.poster_path),
                                contentDescription = "image",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(250.dp)
                            )

                            Row() {

                                Image(painter = rememberImagePainter(data = "https://icons.iconarchive.com/icons/uiconstock/socialmedia/512/IMDb-icon.png"),
                                    contentDescription = "image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(35.dp, 35.dp)
                                )

                                Text(text = selectedMovie.vote_average.toString() + "/10",
                                    modifier = Modifier.padding(7.dp),
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Left
                                )

                                Text(text = selectedMovie.release_date,
                                    modifier = Modifier.padding(7.dp),
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Left
                                )

                            }

                            Text(text = selectedMovie.title,
                                modifier = Modifier.padding(2.dp),
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Left
                            )


                            Text(text = selectedMovie.overview,
                                modifier = Modifier.padding(2.dp),
                                fontWeight = FontWeight.Normal,
                                color = Color.Black,
                                fontSize = 30.sp,
                                textAlign = TextAlign.Left

                            )


                        }

                        is Resource.Error -> {
                            Text(text = movieDetail.message!!)
                        }

                        is Resource.Loading -> {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.primary,
                            )
                        }
                    }
                }
            }
        }
    }
}