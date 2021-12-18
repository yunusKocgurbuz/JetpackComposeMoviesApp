package com.yunuskocgurbuz.jetpackcomposemoviesapp.repository

import com.yunuskocgurbuz.jetpackcomposemoviesapp.model.MoviesList
import com.yunuskocgurbuz.jetpackcomposemoviesapp.service.MoviesAPI
import com.yunuskocgurbuz.jetpackcomposemoviesapp.util.Constants.API_KEY
import com.yunuskocgurbuz.jetpackcomposemoviesapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MoviesRepository @Inject constructor(
    private val api: MoviesAPI
){
    suspend fun getMoviesList(): Resource<MoviesList>{
        val response = try {
            api.getMoviesList(API_KEY)

        }catch (e: Exception){

            return Resource.Error("Error API connect!")
        }


        return Resource.Success(response)
    }

    suspend fun getNowplayingList(): Resource<MoviesList>{
        val response = try {
            api.getNowplayingList(API_KEY)

        }catch (e: Exception){

            return Resource.Error("Error API connect!")
        }


        return Resource.Success(response)
    }
}