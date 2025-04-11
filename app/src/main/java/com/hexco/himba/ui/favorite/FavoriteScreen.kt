package com.hexco.himba.ui.favorite

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hexco.himba.data.util.PAGE_MAX_ITEM
import com.hexco.himba.ui.util.components.MovieCard
import com.hexco.himba.ui.util.components.MovieListGrid

@Composable
fun FavoriteScreen(
  viewModel: FavoriteViewModel = hiltViewModel(),
  navigateToMovieScreen: (Int) -> Unit,
) {

  val state by viewModel.state.collectAsStateWithLifecycle()
  val movies = state.movies

  val lazyGridState = rememberLazyGridState()


  Scaffold { scaffoldPadding ->
    MovieListGrid(
      modifier = Modifier.padding(scaffoldPadding),
      lazyGridState = lazyGridState,
    ) {
      items(movies.size) {
        if (it >= PAGE_MAX_ITEM - 1) {
          viewModel.fetchNextPage()
        }

        MovieCard(
          movie = movies[it],
          navigateToMovieScreen = navigateToMovieScreen
        )
      }
    }
  }
}
