package com.hexco.himba.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexco.himba.domain.history.History
import com.hexco.himba.domain.movie.MovieItem
import com.hexco.himba.domain.repository.HistoryRepository
import com.hexco.himba.domain.repository.MovieRepository
import com.hexco.himba.ui.util.Paginator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
  val movieRepo: MovieRepository,
  val historyRepo: HistoryRepository
) : ViewModel() {

  private val _state = MutableStateFlow(SearchState())
  val state = _state.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery = _searchQuery.asStateFlow()

  private val _searchHistory = MutableStateFlow<List<History>>(emptyList())
  val searchHistory = _searchHistory.asStateFlow()

  private var lastQuery: String = ""

  private lateinit var paginator: Paginator<MovieItem, Int>

  init {
    observeInput()
    getSearchHistory()
  }

  fun onEvent(event: SearchEvent) {
    when (event) {
      is SearchEvent.ChangeQuery -> {
        _state.update {
          it.copy(
            isLoaded = false
          )
        }
        _searchQuery.update { event.query }
      }

      is SearchEvent.Search -> {
        val query = event.query
        if (query.isNotEmpty()) {
          addSearchHistory(query)
        }
        search()
      }

      is SearchEvent.RemoveHistory -> {
        removeSearchHistory(event.query)
      }
    }
  }

  private fun getSearchHistory() {
    viewModelScope.launch {
      historyRepo.getAll()
        .collect { history ->
          _searchHistory.update {
            history
          }
        }
    }
  }

  private fun addSearchHistory(query: String) {
    viewModelScope.launch {
      historyRepo.addHistory(
        History(
          query = query,
          date = System.currentTimeMillis()
        )
      )
    }
  }

  private fun removeSearchHistory(query: String) {
    viewModelScope.launch {
      historyRepo.removeHistory(query)
    }
  }

  fun nextPage() {
    viewModelScope.launch {
      paginator.fetchNextPage()
    }
  }

  private fun observeInput() {
    _searchQuery
      .debounce(800)
      .filter { it.isNotEmpty() }
      .onEach {
        search()
      }
      .launchIn(viewModelScope)
  }

  private fun search() {
    if (searchQuery.value.equals(lastQuery) || searchQuery.value.isEmpty()) return
    _state.update {
      SearchState()
    }
    lastQuery = searchQuery.value

    paginator = Paginator(
      initialKey = 0,
      getNextKey = {
        it + 1
      },
      request = {
        movieRepo.searchMovie(query = searchQuery.value, page = it)
      },
      onLoadUpdated = { load ->
        _state.update {
          it.copy(
            isLoaded = load
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

    nextPage()
  }
}