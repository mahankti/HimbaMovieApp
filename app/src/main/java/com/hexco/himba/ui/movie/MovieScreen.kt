package com.hexco.himba.ui.movie

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hexco.himba.ui.movie.components.ButtonRowSection
import com.hexco.himba.ui.movie.components.DetailSection
import com.hexco.himba.ui.movie.components.OverviewSection
import com.hexco.himba.ui.movie.components.TabSection
import com.hexco.himba.ui.util.LARGE_SPACE
import com.hexco.himba.ui.util.Uri2Image
import com.hexco.himba.ui.util.XLARGE_SPACE
import com.hexco.himba.ui.util.components.LoadingDots
import com.hexco.himba.ui.util.components.TransparentAppBar
import com.hexco.himba.ui.util.fade
import com.hexco.himba.ui.util.path2Uri

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieScreen(
  viewModel: MovieViewModel = hiltViewModel(),
  sessionId: String,
  onBackClick: () -> Unit,
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val movie = state.movie
  Scaffold(
    topBar = {
      TransparentAppBar(
        onBackClick = onBackClick,
        actions = {
          if (sessionId.isNotEmpty()) {
            IconButton(
              modifier = Modifier.clip(CircleShape),
              colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
              ),
              onClick = {
                viewModel.onEvent(MovieEvent.OnFavoriteClick)
              }
            ) {
              Icon(
                imageVector = if (state.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "add to favorite"
              )
            }
          }
        },
      )
    }
  ) {
    if (state.isLoading) {
      Box(
        modifier = Modifier
          .fillMaxSize(),
        contentAlignment = Alignment.Center
      ) {
        LoadingDots()
      }
    } else {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
      ) {
        Uri2Image(
          modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .fade(),
          url = path2Uri(movie!!.backdrop_path, "w780"),
          contentDescription = movie.title,
          alignment = Alignment.Center
        )

        Column(
          modifier = Modifier
            .padding(XLARGE_SPACE.dp)
        ) {
          DetailSection(movie = movie)
          Spacer(modifier = Modifier.height(LARGE_SPACE.dp))
          ButtonRowSection(viewModel = viewModel)
          Spacer(modifier = Modifier.height(LARGE_SPACE.dp))
          OverviewSection(overview = movie.overview)
          Spacer(modifier = Modifier.height(LARGE_SPACE.dp))
          TabSection(modifier = Modifier.height(300.dp))
        }
      }
    }
  }
}