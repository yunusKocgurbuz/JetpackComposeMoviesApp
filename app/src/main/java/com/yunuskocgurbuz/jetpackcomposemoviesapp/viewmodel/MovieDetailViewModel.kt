package com.yunuskocgurbuz.jetpackcomposemoviesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.yunuskocgurbuz.jetpackcomposemoviesapp.model.moviesdetailmodel.MoviesDetailList
import com.yunuskocgurbuz.jetpackcomposemoviesapp.repository.MoviesRepository
import com.yunuskocgurbuz.jetpackcomposemoviesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private  val repository: MoviesRepository
) : ViewModel() {

    suspend fun getMovieDetail(id: String): Resource<MoviesDetailList>{
        return repository.getMovieDetail(id)
    }
}