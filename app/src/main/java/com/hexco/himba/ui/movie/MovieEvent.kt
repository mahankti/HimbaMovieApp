package com.hexco.himba.ui.movie

interface MovieEvent {
  data object OnPlayClick : MovieEvent
  data object OnDownloadClick : MovieEvent
  data object OnFavoriteClick : MovieEvent
}
