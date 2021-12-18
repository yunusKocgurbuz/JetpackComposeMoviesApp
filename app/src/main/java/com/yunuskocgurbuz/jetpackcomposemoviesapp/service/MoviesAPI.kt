package com.yunuskocgurbuz.jetpackcomposemoviesapp.service

import com.yunuskocgurbuz.jetpackcomposemoviesapp.model.moviesdetailmodel.MoviesDetailList
import com.yunuskocgurbuz.jetpackcomposemoviesapp.model.movieslistmodel.MoviesList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    //movie/upcoming?api_key=412d1ae15c8dfe54b79ff21297772818
    @GET("movie/upcoming?")
    suspend fun getMoviesList(
        @Query("api_key") api_key: String

        ) : MoviesList


    //movie/now_playing?api_key=412d1ae15c8dfe54b79ff21297772818
    @GET("movie/now_playing?")
    suspend fun getNowplayingList(
        @Query("api_key") api_key: String

    ) : MoviesList

    //movie/movie_id?api_key=412d1ae15c8dfe54b79ff21297772818
    @GET("movie/{movie_id}?")
    suspend fun getMovieDetail(
        @Path(value = "movie_id", encoded = true) movie_id: String,
        @Query("api_key") api_key: String
    ): MoviesDetailList
}