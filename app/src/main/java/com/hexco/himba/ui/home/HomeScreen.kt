package com.hexco.himba.ui.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hexco.himba.domain.util.ListTitle
import com.hexco.himba.ui.home.components.CategoryDiv
import com.hexco.himba.ui.home.components.HeaderSlideShow
import com.hexco.himba.ui.util.LARGE_SPACE

@Composable
fun HomeScreen(
  viewModel: HomeViewModel = hiltViewModel(),
  navigateToMovieScreen: (Int) -> Unit,
  navigateToMovieListScreen: (ListTitle) -> Unit,
) {
  val state by viewModel.homeState.collectAsStateWithLifecycle()

  LazyColumn {
    item {
      HeaderSlideShow(
        items = state.slideShowContent,
        onItemClick = navigateToMovieScreen
      )
    }

    item {
      CategoryDiv(
        categoryTag = "Popular",
        items = state.section_1,
        onMoreClick = {
          navigateToMovieListScreen(ListTitle.Popular)
        },
        navigateToMovieScreen = navigateToMovieScreen
      )
      Spacer(Modifier.height(LARGE_SPACE.dp))
    }


    item {
      CategoryDiv(
        categoryTag = "Top Rated",
        items = state.section_2,
        onMoreClick = {
          navigateToMovieListScreen(ListTitle.TopRated)
        },
        navigateToMovieScreen = navigateToMovieScreen
      )
      Spacer(Modifier.height(LARGE_SPACE.dp))
    }

    item {
      CategoryDiv(
        categoryTag = "Best Selling",
        items = state.section_3,
        onMoreClick = {
          navigateToMovieListScreen(ListTitle.BestSelling)
        },
        navigateToMovieScreen = navigateToMovieScreen
      )
    }
  }
}
