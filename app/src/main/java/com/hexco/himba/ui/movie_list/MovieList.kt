package com.hexco.himba.ui.movie_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hexco.himba.data.util.PAGE_MAX_ITEM
import com.hexco.himba.ui.util.components.MovieCard
import com.hexco.himba.ui.util.components.MovieListGrid
import com.hexco.himba.ui.util.components.TransparentAppBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieList(
  viewModel: MovieListViewModel = hiltViewModel(),
  navigateToMovieScreen: (Int) -> Unit,
  onBackClick: () -> Unit,
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val movies = state.movies

  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
  val lazyGridState = rememberLazyGridState()

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      TransparentAppBar(
        title = viewModel.listTitle.name,
        transparency = 1f,
        scrollBehavior = scrollBehavior,
        onBackClick = onBackClick
      )
    }
  ) { scaffoldPadding ->
    MovieListGrid(
      modifier = Modifier.padding(scaffoldPadding),
      lazyGridState = lazyGridState,
    ) {
      items(movies.size) {
        if (it >= PAGE_MAX_ITEM) {
          viewModel.fetchNextPage()
        }

        MovieCard(
          movie = movies[it],
          navigateToMovieScreen = navigateToMovieScreen
        )
      }

      if (state.isLoading) {
        item(
          span = {
            GridItemSpan(maxLineSpan)
          }
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            CircularProgressIndicator()
          }
        }
      }
    }
  }
}