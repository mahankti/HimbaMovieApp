package com.hexco.himba.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hexco.himba.data.util.DataStoreManager
import com.hexco.himba.domain.repository.MovieRepository
import com.hexco.himba.domain.repository.ProfileRepository
import com.hexco.himba.domain.util.Resource
import com.hexco.himba.ui.util.MovieScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val dataStoreManager: DataStoreManager,
  private val movieRepository: MovieRepository,
  private val profileRepository: ProfileRepository,
) : ViewModel() {

  val movieId = savedStateHandle.toRoute<MovieScreen>().movieId

  var _state = MutableStateFlow(MovieState())
  val state: StateFlow<MovieState> = _state

  init {
    getMovie()
  }

  fun onEvent(event: MovieEvent) {
    when (event) {
      MovieEvent.OnFavoriteClick -> {
        viewModelScope.launch {
          if (state.value.isFavorite) {
            profileRepository.changeFavorite(false, movieId, dataStoreManager.sessionId.first())
          } else {
            profileRepository.changeFavorite(true, movieId, dataStoreManager.sessionId.first())
          }
          getStatus()
        }
      }
    }
  }

  private suspend fun getStatus() {
    val favorite = movieRepository.getMovieStatus(movieId, dataStoreManager.sessionId.first())
    when (favorite) {
      is Resource.Error,
      is Resource.Success -> {
        _state.update {
          it.copy(isFavorite = favorite.data!!)
        }
      }
    }
  }

  private fun getMovie() {
    viewModelScope.launch {
      val result = movieRepository.getMovie(movieId)

      when (result) {
        is Resource.Error -> {
          _state.update {
            MovieState(
              error = result.message
            )
          }
          delay(3000)
          getMovie()
        }

        is Resource.Success -> {
          _state.update {
            MovieState(
              movie = result.data,
              isLoading = false
            )
          }
        }
      }
      getStatus()
    }
  }
}