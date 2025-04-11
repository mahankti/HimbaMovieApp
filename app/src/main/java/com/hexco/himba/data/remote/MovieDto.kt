package com.hexco.himba.data.remote

import com.squareup.moshi.Json

data class MovieListDto(
  @Json(name = "results")
  val movies: List<MovieDto>
)

data class MovieDto(
  val id: Int,
  val title: String,
  val backdrop_path: String?,
  val genre_ids: List<Int>,
  val original_language: String,
  val poster_path: String?,
  val release_date: String,
)

data class GenreDto(
  val name: String
)

data class DetailedMovieDto(
  val id: Int,
  val adult: Boolean,
  val backdrop_path: String?,
  val genres: List<GenreDto>,
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

data class MovieStatus(
  val favorite: Boolean
)