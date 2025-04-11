package com.hexco.himba.domain.movie

data class Movie(
  val id: Int,
  val backdrop_path: String?,
  val genres: List<String>,
  val origin_country: List<String>,
  val original_language: String,
  val original_title: String,
  val overview: String,
  val poster_path: String?,
  val release_date: String,
  val status: String,
  val tagline: String,
  val title: String,
  val vote_average: Double,
  val vote_count: Int
)