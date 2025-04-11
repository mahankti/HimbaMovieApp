package com.hexco.himba.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hexco.himba.domain.repository.MovieRepository
import com.hexco.himba.domain.util.ListTitle
import com.hexco.himba.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val repository: MovieRepository
) : ViewModel() {

  val _homeState = MutableStateFlow(HomeState())
  val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

  init {
    retrieveContent()
  }

  fun retrieveContent() {
    viewModelScope.launch {
      when (val result = repository.getMovieList()) {
        is Resource.Success -> {
          _homeState.update {
            it.copy(
              slideShowContent = repository.getMovieList(listTitle = ListTitle.BestSelling).data ?: emptyList(),
              section_1 = repository.getMovieList(listTitle = ListTitle.Popular).data ?: emptyList(),
              section_2 = repository.getMovieList(listTitle = ListTitle.TopRated).data ?: emptyList(),
              section_3 = repository.getMovieList(listTitle = ListTitle.BestSelling).data ?: emptyList(),
            )
          }
        }

        is Resource.Error -> {
          _homeState.update {
            it.copy(
              error = result.message
            )
          }
        }
      }

    }
  }
}