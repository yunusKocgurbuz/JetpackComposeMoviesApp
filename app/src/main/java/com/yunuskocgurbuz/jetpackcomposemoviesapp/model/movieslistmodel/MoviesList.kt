package com.yunuskocgurbuz.jetpackcomposemoviesapp.model.movieslistmodel

data class MoviesList(
    val dates: Dates,
    val page: Int,
    val results: List<ResultMovie>,
    val total_pages: Int,
    val total_results: Int
)