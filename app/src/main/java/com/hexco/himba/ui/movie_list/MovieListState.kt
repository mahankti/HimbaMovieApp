package com.hexco.himba.ui.movie_list

import com.hexco.himba.domain.movie.MovieItem

data class MovieListState(
  val movies: List<MovieItem> = emptyList(),
  val isLoading: Boolean = true,
  val error: String? = null,
)