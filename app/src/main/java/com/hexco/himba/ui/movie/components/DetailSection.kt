package com.hexco.himba.ui.movie.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hexco.himba.domain.movie.Movie

@Composable
fun DetailSection(
  movie: Movie
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = movie.title,
      fontWeight = FontWeight.Bold,
      fontSize = 26.sp,
      textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(8.dp))
    Row {
      Text(
        text = movie.release_date.substring(0..3),
        color = Color.LightGray,
        fontSize = 12.sp,
      )
      Text(
        text = " · "
      )
      Text(
        text = movie.genres.toString().replace(Regex("[\\[\\]]"), ""),
        color = Color.LightGray,
        fontSize = 12.sp,
      )
    }
  }
}