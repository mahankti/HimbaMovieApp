package com.hexco.himba.ui.movie.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hexco.himba.ui.movie.MovieEvent
import com.hexco.himba.ui.movie.MovieViewModel
import com.hexco.himba.ui.theme.primaryColor

@Composable
fun ButtonRowSection(
  viewModel: MovieViewModel
) {
  Row(
    modifier = Modifier.fillMaxWidth()
  ) {
    Button(
      modifier = Modifier.weight(0.5f),
      colors = ButtonDefaults.buttonColors(
        containerColor = primaryColor,
        contentColor = Color.White
      ),
      onClick = {
        viewModel.onEvent(MovieEvent.OnPlayClick)
      }
    ) {
      Icon(
        imageVector = Icons.Default.PlayArrow,
        contentDescription = "Play",
      )
      Spacer(Modifier.width(6.dp))
      Text("Play")
    }
    Spacer(Modifier.width(12.dp))
    Button(
      modifier = Modifier.weight(0.5f),
      colors = ButtonDefaults.buttonColors(
        containerColor = Color.White.copy(alpha = 0.05f),
        contentColor = Color.White
      ),
      onClick = {
        viewModel.onEvent(MovieEvent.OnDownloadClick)
      }
    ) {
      Icon(
        imageVector = Icons.Default.ArrowCircleDown,
        contentDescription = "Download",
      )
      Spacer(Modifier.width(6.dp))
      Text("Download")
    }
  }
}