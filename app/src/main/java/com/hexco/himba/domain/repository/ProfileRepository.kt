package com.hexco.himba.domain.repository

import com.hexco.himba.data.remote.Profile
import com.hexco.himba.domain.movie.MovieItem
import com.hexco.himba.domain.util.Resource

interface ProfileRepository {
  suspend fun getProfile(sessionId: String): Resource<Profile>
  suspend fun changeFavorite(favorite: Boolean, movieId: Int, sessionId: String): Resource<Boolean>
  suspend fun getFavoriteMovies(page: Int, sessionId: String): Resource<List<MovieItem>>
}