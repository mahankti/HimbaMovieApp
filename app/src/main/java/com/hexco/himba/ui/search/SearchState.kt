package com.hexco.himba.ui.search

import com.hexco.himba.domain.movie.MovieItem

data class SearchState(
  val movies: List<MovieItem> = emptyList(),
  val isLoaded: Boolean = false,
  val error: String? = null,
  val endReached: Boolean = false,
)