package com.hexco.himba.data.remote

import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
  @GET("discover/movie?include_adult=false&include_video=false&language=en-US")
  suspend fun getMovieList(
    @HeaderMap header: Map<String, String>,
    @Query("page") page: Int,
    @Query("sort_by") sort: String,
  ): MovieListDto

  @GET("search/movie?include_adult=false&language=en-US")
  suspend fun searchMovie(
    @HeaderMap header: Map<String, String>,
    @Query("query") query: String,
    @Query("page") page: Int,
  ): MovieListDto

  @GET("movie/now_playing?language=en-US")
  suspend fun getNowPlaying(
    @HeaderMap header: Map<String, String>,
    @Query("page") page: Int,
  ): MovieListDto

  @GET("movie/popular?language=en-US")
  suspend fun getPopular(
    @HeaderMap header: Map<String, String>,
    @Query("page") page: Int,
  ): MovieListDto

  @GET("movie/top_rated?language=en-US")
  suspend fun getTopRated(
    @HeaderMap header: Map<String, String>,
    @Query("page") page: Int,
  ): MovieListDto

  @GET("movie/{movie_id}?language=en-US")
  suspend fun getMovie(
    @HeaderMap header: Map<String, String>,
    @Path("movie_id") movieId: Int,
  ): DetailedMovieDto

  @GET("movie/{movie_id}/account_states")
  suspend fun movieStatus(
    @HeaderMap header: Map<String, String>,
    @Path("movie_id") movieId: Int,
    @Query("session_id") sessionId: String
  ): MovieStatus
}