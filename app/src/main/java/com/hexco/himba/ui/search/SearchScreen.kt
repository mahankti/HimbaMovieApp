package com.hexco.himba.ui.search

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hexco.himba.data.util.PAGE_MAX_ITEM
import com.hexco.himba.domain.history.History
import com.hexco.himba.domain.movie.MovieItem
import com.hexco.himba.ui.search.components.AppSearchBar
import com.hexco.himba.ui.theme.NoRippleInteractionSource
import com.hexco.himba.ui.theme.Typography
import com.hexco.himba.ui.util.MEDIUM_SPACE
import com.hexco.himba.ui.util.components.MovieCard
import com.hexco.himba.ui.util.components.MovieListGrid

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
  viewModel: SearchViewModel = hiltViewModel(),
  navigateToMovieScreen: (Int) -> Unit,
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
  val searchHistory by viewModel.searchHistory.collectAsStateWithLifecycle()
  val movies = state.movies

  Scaffold(
    content = {},
    topBar = {
      AppSearchBar(
        query = searchQuery,
        onQueryChange = {
          viewModel.onEvent(SearchEvent.ChangeQuery(it))
        },
        onSearch = {
          viewModel.onEvent(SearchEvent.Search(it))
        },
      ) {
        if (searchQuery.isEmpty()) {
          RecentSearch(
            history = searchHistory,
            onQueryChange = {
              viewModel.onEvent(SearchEvent.ChangeQuery(it))
            },
            onRemoveHistory = {
              viewModel.onEvent(SearchEvent.RemoveHistory(it))
            }
          )
        } else if (state.error != null) {
          NotFoundScreen()
        } else if (state.movies.isNotEmpty()) {
          SearchResult(
            result = movies,
            navigateToMovieScreen = navigateToMovieScreen
          ) {
            viewModel.nextPage()
          }
        }
      }
    }
  )
}

@Composable
fun SearchResult(
  result: List<MovieItem>,
  navigateToMovieScreen: (Int) -> Unit,
  requestNextPage: () -> Unit
) {
  val lazyGridState = rememberLazyGridState()
  MovieListGrid(
    lazyGridState = lazyGridState
  ) {
    items(result.size) {
      if (it >= PAGE_MAX_ITEM - 1) requestNextPage()

      MovieCard(
        movie = result[it],
        navigateToMovieScreen = navigateToMovieScreen
      )
    }
  }
}

@Composable
fun RecentSearch(
  history: List<History>,
  onQueryChange: (String) -> Unit,
  onRemoveHistory: (String) -> Unit
) {
  LazyColumn(
    modifier = Modifier.padding(MEDIUM_SPACE.dp)
  ) {
    if (!history.isEmpty()) {
      item {
        Text(
          text = "Recent Search",
          style = Typography.titleMedium
        )
      }
    }
    items(history) { history ->
      ListItem(
        modifier = Modifier
          .clickable {
            onQueryChange(history.query)
          },
        headlineContent = {
          Text(text = history.query)
        },
        leadingContent = {
          Icon(imageVector = Icons.Default.Restore, contentDescription = null)
        },
        trailingContent = {
          IconButton(
            interactionSource = NoRippleInteractionSource,
            onClick = {
              onRemoveHistory(history.query)
            }
          ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Remove")
          }
        },
        colors = ListItemDefaults.colors(
          containerColor = Color.Transparent
        ),
      )
    }
  }
}

@Composable
fun NotFoundScreen() {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Column {
      Text(
        text = "Not Found!",
        fontSize = 24.sp,
        color = Color.Gray
      )
    }
  }
}
