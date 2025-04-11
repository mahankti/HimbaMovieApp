package com.hexco.himba.ui.util

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.hexco.himba.domain.util.IMAGE_BASE_URL
import com.hexco.himba.ui.theme.background

fun path2Uri(imagePath: String?, size: String = ""): String {
  if (imagePath == null) return "https://f4.bcbits.com/img/a0637362742_65"

  if (size.equals("")) {
    return IMAGE_BASE_URL + "original" + imagePath
  }
  return IMAGE_BASE_URL + size + imagePath
}

fun genreList2Text(genreList: List<Int>): String {
  val genreMap = mapOf(
    28 to "Action",
    12 to "Adventure",
    16 to "Animation",
    35 to "Comedy",
    80 to "Crime",
    99 to "Documentary",
    18 to "Drama",
    10751 to "Family",
    14 to "Fantasy",
    36 to "History",
    27 to "Horror",
    10402 to "Music",
    9648 to "Mystery",
    10749 to "Romance",
    878 to "Science Fiction",
    10770 to "TV Movie",
    53 to "Thriller",
    10752 to "War",
    37 to "Western"
  )

  var mappedList = genreList.mapNotNull { genreMap[it] }
  while (mappedList.size > 4) {
    mappedList = mappedList.subList(0, 4)
  }

  val result = mappedList.toString()
    .replace(Regex("[\\[\\]]"), "")
    .replace(", ", " · ")
  return result
}

@Composable
fun Modifier.shimmerEffect(): Modifier = composed {
  var size by remember {
    mutableStateOf(IntSize.Zero)
  }
  val transition = rememberInfiniteTransition()
  val brushOffset by transition.animateFloat(
    initialValue = -3 * size.width.toFloat(),
    targetValue = 3 * size.width.toFloat(),
    animationSpec = infiniteRepeatable(
      animation = tween(1000),
    )
  )

  background(
    brush = Brush.linearGradient(
      colors = listOf(
        Color(0x0FD2D2D2),
        Color(0xB7C4C4C4),
        Color(0x0FD2D2D2),
      ),
      start = Offset(brushOffset, 0f),
      end = Offset(brushOffset + size.width, size.height.toFloat())
    )
  ).onGloballyPositioned {
    size = it.size
  }
}

@Composable
fun Modifier.fade(): Modifier = this
  .drawWithContent {
    drawContent()
    drawRect(
      alpha = 0.99f, brush = Brush.verticalGradient(
        listOf(Color.Transparent, background), startY = size.height / 2
      )
    )
  }

@Composable
fun Uri2Image(
  modifier: Modifier = Modifier,
  contentDescription: String? = null,
  contentScale: ContentScale = ContentScale.Crop,
  alignment: Alignment = Alignment.Center,
  url: String,
) {
  val painter = rememberAsyncImagePainter(
    model = ImageRequest.Builder(LocalPlatformContext.current)
      .data(url)
      .build(),
  )

  val state by painter.state.collectAsState()

  when (state) {
    AsyncImagePainter.State.Empty,
    is AsyncImagePainter.State.Error -> {
      painter.onForgotten()
      painter.onRemembered()
    }

    is AsyncImagePainter.State.Loading -> {
      Box(
        modifier = modifier.shimmerEffect()
      )
    }

    is AsyncImagePainter.State.Success -> {
      Image(
        modifier = modifier,
        painter = painter,
        contentScale = contentScale,
        contentDescription = contentDescription,
        alignment = alignment
      )
    }
  }
}