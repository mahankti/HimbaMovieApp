package com.hexco.himba.data.repository

import coil3.network.HttpException
import com.hexco.himba.data.remote.FavoriteDetail
import com.hexco.himba.data.remote.Profile
import com.hexco.himba.data.remote.ProfileApi
import com.hexco.himba.data.repository.MovieRepositoryImpl.Companion.defaultHeader
import com.hexco.himba.domain.movie.MovieItem
import com.hexco.himba.domain.repository.ProfileRepository
import com.hexco.himba.domain.util.Resource
import com.hexco.himba.domain.util.toModel
import java.io.IOException

class ProfileRepositoryImpl(
  val profileApi: ProfileApi
) : ProfileRepository {
  override suspend fun getProfile(sessionId: String): Resource<Profile> {
    return try {
      val data = profileApi.getProfile(header = defaultHeader, sessionId = sessionId)
      Resource.Success(data = data)
    } catch (e: HttpException) {
      Resource.Error(message = e.message ?: "Network Error")
    } catch (e: IOException) {
      Resource.Error(message = e.message ?: "Unknown Error")
    }
  }

  override suspend fun changeFavorite(favorite: Boolean, movieId: Int, sessionId: String): Resource<Boolean> {
    return try {
      val data = profileApi.addFavorite(
        header = defaultHeader, sessionId = sessionId,
        favoriteDetail = FavoriteDetail(media_type = "movie", media_id = movieId, favorite = favorite)
      )
      Resource.Success(data = data.success)
    } catch (e: HttpException) {
      Resource.Error(message = e.message ?: "Network Error")
    } catch (e: IOException) {
      Resource.Error(message = e.message ?: "Unknown Error")
    }
  }

  override suspend fun getFavoriteMovies(page: Int, sessionId: String): Resource<List<MovieItem>> {
    return try {
      val data = profileApi.getFavoriteMovies(header = defaultHeader, page = page, sessionId = sessionId)
      Resource.Success(data = data.movies.map { it.toModel() })
    } catch (e: HttpException) {
      Resource.Error(message = e.message ?: "Network Error")
    } catch (e: IOException) {
      Resource.Error(message = e.message ?: "Unknown Error")
    }
  }


}