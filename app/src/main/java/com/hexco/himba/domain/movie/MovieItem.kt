package com.hexco.himba.domain.movie

data class MovieItem(
  val id: Int,
  val title: String,
  val backdrop_path: String?,
  val genre_ids: List<Int>,
  val original_language: String,
  val poster_path: String?,
  val release_date: String,
)