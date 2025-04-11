package com.hexco.himba.ui.util.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.hexco.himba.ui.theme.Typography
import com.hexco.himba.ui.theme.background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentAppBar(
  title: String = "",
  transparency: Float = 0.0f,
  scrollBehavior: TopAppBarScrollBehavior? = null,
  onBackClick: () -> Unit = {},
  actions: @Composable (RowScope.() -> Unit) = {},
) {
  CenterAlignedTopAppBar(
    title = {
      Text(
        text = title,
        style = Typography.titleMedium
      )
    },
    navigationIcon = {
      IconButton(
        modifier = Modifier.clip(CircleShape),
        colors = IconButtonDefaults.iconButtonColors(
          containerColor = Color.Transparent,
        ),
        onClick = {
          onBackClick()
        }
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
          contentDescription = "Back"
        )
      }
    },
    scrollBehavior = scrollBehavior,
    actions = actions,
    colors = TopAppBarDefaults.topAppBarColors(
      navigationIconContentColor = Color.White,
      containerColor = background.copy(alpha = transparency),
      scrolledContainerColor = background.copy(alpha = transparency),
    ),
  )
}