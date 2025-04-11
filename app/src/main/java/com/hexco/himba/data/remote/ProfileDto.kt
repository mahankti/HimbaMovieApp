package com.hexco.himba.data.remote

import com.squareup.moshi.Json

data class FavoriteDetail(
  val media_type: String,
  val media_id: Int,
  val favorite: Boolean
)

data class Profile(
  val avatar: Avatar,
  val name: String,
  val username: String,
) {
  data class Avatar(
    val tmdb: TmdbAvatar
  ) {
    data class TmdbAvatar(
      @Json(name = "avatar_path") val avatarPath: String?
    )
  }
}



