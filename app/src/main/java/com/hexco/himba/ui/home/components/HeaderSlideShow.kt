package com.hexco.himba.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hexco.himba.domain.movie.MovieItem
import com.hexco.himba.ui.theme.primaryColor
import com.hexco.himba.ui.util.Uri2Image
import com.hexco.himba.ui.util.XLARGE_SPACE
import com.hexco.himba.ui.util.fade
import com.hexco.himba.ui.util.genreList2Text
import com.hexco.himba.ui.util.path2Uri

@Composable
fun HeaderSlideShow(
  items: List<MovieItem>,
  onItemClick: (movieId: Int) -> Unit
) {
  val pagerState = rememberPagerState(pageCount = { items.size * 1000 })

  HorizontalPager(
    modifier = Modifier.height(400.dp),
    beyondViewportPageCount = 1,
    state = pagerState,
  ) {
    val index = it % (items.size)
    var movie = items[index]
    Box(
      modifier = Modifier.clickable {
        onItemClick(movie.id)
      }
    ) {
      Uri2Image(
        modifier = Modifier
          .fillMaxSize()
          .fade(),
        url = path2Uri(movie.backdrop_path),
        contentDescription = movie.title,
        alignment = Alignment.BottomCenter
      )

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .height(100.dp)
          .padding(XLARGE_SPACE.dp)
          .align(Alignment.BottomStart)
      ) {
        Text(
          modifier = Modifier
            .fillMaxSize()
            .weight(0.5f),
          style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
          text = movie.title,
          overflow = TextOverflow.Ellipsis
        )

        Row(
          modifier = Modifier.weight(0.5f),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            modifier = Modifier.weight(0.8f),
            style = MaterialTheme.typography.labelSmall,
            text = genreList2Text(movie.genre_ids),
            color = Color.LightGray
          )

          PageIndexer(
            modifier = Modifier.weight(0.2f),
            pageNumber = index + 1,
            lastPage = items.size
          )
        }
      }
    }
  }
}

@Composable
fun PageIndexer(
  modifier: Modifier = Modifier,
  pageNumber: Int,
  lastPage: Int
) {
  val fontStyle = MaterialTheme.typography.labelSmall
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.End,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = if (pageNumber == lastPage) (lastPage - 1).toString() else pageNumber.toString(),
      color = if (pageNumber == lastPage) Color.LightGray else primaryColor,
      style = fontStyle
    )
    Text(
      text = " · ",
      color = Color.LightGray,
    )
    Text(
      text = lastPage.toString(),
      color = if (pageNumber == lastPage) primaryColor else Color.LightGray,
      style = fontStyle
    )
  }
}