package com.yunuskocgurbuz.jetpackcomposemoviesapp.service

import com.yunuskocgurbuz.jetpackcomposemoviesapp.model.MoviesList
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    //https://api.themoviedb.org/3/movie/upcoming?api_key=412d1ae15c8dfe54b79ff21297772818
    @GET("movie/upcoming?")
    suspend fun getMoviesList(
        @Query("api_key") api_key: String

        ) : MoviesList


    //https://api.themoviedb.org/3/movie/now_playing?api_key=412d1ae15c8dfe54b79ff21297772818
    @GET("movie/now_playing?")
    suspend fun getNowplayingList(
        @Query("api_key") api_key: String

    ) : MoviesList
}