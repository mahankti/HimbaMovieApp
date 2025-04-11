package com.hexco.himba.ui.favorite

import com.hexco.himba.domain.movie.MovieItem

data class FavoriteState(
  val movies: List<MovieItem> = emptyList(),
  val isLoading: Boolean = true,
  val error: String? = null,
)
