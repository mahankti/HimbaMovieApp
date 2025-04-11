package com.hexco.himba.domain.repository

import com.hexco.himba.domain.movie.Movie
import com.hexco.himba.domain.movie.MovieItem
import com.hexco.himba.domain.util.ListSort
import com.hexco.himba.domain.util.ListTitle
import com.hexco.himba.domain.util.Resource

interface MovieRepository {
  suspend fun getMovieList(listTitle: ListTitle? = null, page: Int = 1, sort: ListSort = ListSort.POPULARITY_DESC): Resource<List<MovieItem>>
  suspend fun searchMovie(query: String = "", page: Int = 1): Resource<List<MovieItem>>
  suspend fun getMovie(movieId: Int): Resource<Movie>
  suspend fun getMovieStatus(movieId: Int, sessionId: String): Resource<Boolean>
}