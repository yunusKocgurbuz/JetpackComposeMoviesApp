package com.yunuskocgurbuz.jetpackcomposemoviesapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunuskocgurbuz.jetpackcomposemoviesapp.model.movieslistmodel.ResultMovie
import com.yunuskocgurbuz.jetpackcomposemoviesapp.repository.MoviesRepository
import com.yunuskocgurbuz.jetpackcomposemoviesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel  @Inject constructor(
    private val repository: MoviesRepository
): ViewModel() {

    var moviesList = mutableStateOf<List<ResultMovie>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)


    init {
        loadMovies()
    }


    fun loadMovies(){
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getMoviesList()

            when(result){
                is Resource.Success -> {
                    val newsItems = result.data!!.results

                    errorMessage.value = ""
                    isLoading.value = false
                    moviesList.value += newsItems

                }

                is Resource.Error -> {

                    errorMessage.value = result.message!!
                    isLoading.value = false
                }
            }

        }
    }
}