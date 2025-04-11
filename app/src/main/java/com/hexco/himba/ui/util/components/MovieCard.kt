package com.hexco.himba.ui.util.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hexco.himba.domain.movie.MovieItem
import com.hexco.himba.ui.util.Uri2Image
import com.hexco.himba.ui.util.path2Uri

@Composable
fun MovieCard(
  modifier: Modifier = Modifier,
  movie: MovieItem,
  navigateToMovieScreen: (Int) -> Unit,
) {
  Card(
    modifier = modifier
      .aspectRatio(0.55f)
      .clickable {
        navigateToMovieScreen(movie.id)
      },
    shape = RectangleShape,
    colors = CardDefaults.cardColors(
      containerColor = Color.Transparent,
      contentColor = Color.White
    )
  ) {
    Uri2Image(
      modifier = Modifier
        .clip(RoundedCornerShape(4))
        .weight(0.75f),
      url = path2Uri(movie.poster_path, "w500"),
      contentDescription = movie.title,
    )

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .weight(0.25f),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = movie.title,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelLarge,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
      )

      Spacer(Modifier.height(6.dp))

      var releaseDate = "-"
      if (movie.release_date.length >= 4) releaseDate = movie.release_date.substring(0..3)
      Text(
        text = "${releaseDate} | ${movie.original_language.toUpperCase()}",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelSmall,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
      )
    }
  }
}
