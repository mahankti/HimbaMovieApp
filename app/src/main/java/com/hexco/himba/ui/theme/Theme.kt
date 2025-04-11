package com.hexco.himba.ui.theme

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

private val appScheme = darkColorScheme(
  primary = primaryColor,
  background = background,
  primaryContainer = primaryContainer
)

@Composable
fun HimbaTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) {
  val colorScheme = appScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

object NoRippleInteractionSource : MutableInteractionSource {
  override val interactions: Flow<Interaction> = emptyFlow()

  override suspend fun emit(interaction: Interaction) {}

  override fun tryEmit(interaction: Interaction) = true
}

