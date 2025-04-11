package com.hexco.himba.ui.movie_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hexco.himba.domain.repository.MovieRepository
import com.hexco.himba.ui.util.MovieList
import com.hexco.himba.ui.util.Paginator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val repository: MovieRepository,
) : ViewModel() {

  val listTitle = savedStateHandle.toRoute<MovieList>().listTitle

  private var _state = MutableStateFlow(MovieListState())
  val state = _state.asStateFlow()

  private val paginator = Paginator(
    initialKey = 0,
    getNextKey = {
      it + 1
    },
    request = {
      repository.getMovieList(listTitle = listTitle, page = it)
    },
    onLoadUpdated = { load ->
      _state.update {
        it.copy(
          isLoading = load
        )
      }
    },
    onSuccess = { result ->
      _state.update {
        it.copy(
          movies = state.value.movies + (result ?: emptyList()),
        )
      }
    },
    onError = { error ->
      _state.update {
        it.copy(
          error = error
        )
      }
    },
  )

  init {
    fetchNextPage()
  }

  fun fetchNextPage() {
    viewModelScope.launch {
      paginator.fetchNextPage()
    }
  }
}