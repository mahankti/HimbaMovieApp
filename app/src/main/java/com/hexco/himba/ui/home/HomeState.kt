package com.hexco.himba.ui.home

import com.hexco.himba.domain.movie.MovieItem

data class HomeState(
  val slideShowContent: List<MovieItem> = emptyList(),
  val section_1: List<MovieItem> = emptyList(),
  val section_2: List<MovieItem> = emptyList(),
  val section_3: List<MovieItem> = emptyList(),

  val isLoading: Boolean = false,
  val error: String? = null,
)