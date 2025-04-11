package com.hexco.himba.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hexco.himba.domain.movie.MovieItem
import com.hexco.himba.ui.theme.primaryColor
import com.hexco.himba.ui.util.MEDIUM_SPACE
import com.hexco.himba.ui.util.components.MovieCard

@Composable
fun <T> CategoryDiv(
  categoryTag: String,
  items: List<T>,
  navigateToMovieScreen: (Int) -> Unit,
  onMoreClick: () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .height(290.dp)
      .padding(horizontal = MEDIUM_SPACE.dp)

  ) {
    Row(
      modifier = Modifier
        .weight(0.2f),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      Text(
        text = categoryTag,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = primaryColor
      )
      Spacer(modifier = Modifier.weight(1f))
      IconButton(
        modifier = Modifier.aspectRatio(1f),
        onClick = {
          onMoreClick()
        }
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
          contentDescription = "More"
        )
      }
    }

    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .weight(0.8f),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(items, key = { item ->
        when (item) {
          is MovieItem -> item.id
          else -> {}
        }
      }) { item ->
        when (item) {
          is MovieItem -> {
            MovieCard(
              modifier = Modifier
                .fillMaxHeight()
                .animateItem(),
              movie = item,
              navigateToMovieScreen = navigateToMovieScreen
            )
          }
        }
      }
    }
  }
}