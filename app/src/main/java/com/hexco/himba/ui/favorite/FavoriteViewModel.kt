package com.hexco.himba.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexco.himba.data.util.DataStoreManager
import com.hexco.himba.domain.repository.ProfileRepository
import com.hexco.himba.ui.util.Paginator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
  private val repository: ProfileRepository,
  private val dataStoreManager: DataStoreManager
) : ViewModel() {
  private var _state = MutableStateFlow(FavoriteState())
  val state = _state.asStateFlow()

  private val paginator = Paginator(
    initialKey = 0,
    getNextKey = {
      it + 1
    },
    request = {
      repository.getFavoriteMovies(page = it, dataStoreManager.sessionId.first())
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