package com.hexco.himba.data.remote

import com.hexco.himba.data.util.Validation
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

interface ProfileApi {

  @GET("account/21800203")
  suspend fun getProfile(
    @HeaderMap header: Map<String, String>,
    @Query("session_id") sessionId: String
  ): Profile

  @POST("account/21800203/favorite")
  suspend fun addFavorite(
    @HeaderMap header: Map<String, String>,
    @Query("session_id") sessionId: String,
    @Body favoriteDetail: FavoriteDetail
  ): Validation

  @GET("account/21800203/favorite/movies?language=en-US&sort_by=created_at.desc")
  suspend fun getFavoriteMovies(
    @HeaderMap header: Map<String, String>,
    @Query("page") page: Int,
    @Query("session_id") sessionId: String
  ): MovieListDto
}