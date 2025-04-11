package com.hexco.himba.domain.util

import com.hexco.himba.data.remote.MovieDto
import com.hexco.himba.domain.movie.MovieItem

fun MovieDto.toModel(): MovieItem = MovieItem(
  id = this.id,
  title = this.title,
  backdrop_path = this.backdrop_path,
  genre_ids = this.genre_ids,
  original_language = this.original_language,
  poster_path = this.poster_path,
  release_date = this.release_date
)