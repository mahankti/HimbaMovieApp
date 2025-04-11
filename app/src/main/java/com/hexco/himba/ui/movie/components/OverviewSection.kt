package com.hexco.himba.ui.movie.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hexco.himba.ui.util.MEDIUM_SPACE
import com.webtoonscorp.android.readmore.foundation.ReadMoreTextOverflow
import com.webtoonscorp.android.readmore.foundation.ToggleArea
import com.webtoonscorp.android.readmore.material3.ReadMoreText

@Composable
fun OverviewSection(
  modifier: Modifier = Modifier,
  overview: String
) {
  var (expanded, onExpandedChange) = rememberSaveable { mutableStateOf(false) }

  ReadMoreText(
    modifier = modifier
      .animateContentSize(),
    text = overview,
    expanded = expanded,
    onExpandedChange = onExpandedChange,
    color = Color.LightGray,
    fontSize = 12.sp,
    contentPadding = PaddingValues(MEDIUM_SPACE.dp),

    readMoreText = "   Read more",
    readMoreMaxLines = 3,
    readMoreColor = Color.White,
    readMoreFontWeight = FontWeight.Bold,
    readMoreOverflow = ReadMoreTextOverflow.Ellipsis,
    readMoreFontSize = 12.sp,

    readLessText = "    Read less",
    readLessColor = Color.White,
    readLessFontWeight = FontWeight.Bold,
    readLessFontSize = 12.sp,

    toggleArea = ToggleArea.All
  )
}