package com.hexco.himba.ui.movie

import com.hexco.himba.domain.movie.Movie

data class MovieState(
  val movie: Movie? = null,
  val error: String? = null,
  val isLoading: Boolean = true,
  val isFavorite: Boolean = false,
)