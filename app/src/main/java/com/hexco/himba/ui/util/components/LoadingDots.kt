package com.hexco.himba.ui.util.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingDots() {
  val delayUnit = 300
  val minAlpha = 0.1f

  @Composable
  fun Dot(
    alpha: Float
  ) = Spacer(
    Modifier
      .size(12.dp)
      .alpha(alpha)
      .background(
        color = Color.White,
        shape = CircleShape
      )
  )

  val infiniteTransition = rememberInfiniteTransition()

  @Composable
  fun animateAlphaWithDelay(delay: Int) = infiniteTransition.animateFloat(
    initialValue = minAlpha,
    targetValue = minAlpha,
    animationSpec = infiniteRepeatable(
      animation = keyframes {
        durationMillis = delayUnit * 4
        minAlpha at delay with LinearEasing
        1f at delay + delayUnit with LinearEasing
        minAlpha at delay + delayUnit * 2
      }
    )
  )

  val alpha1 by animateAlphaWithDelay(0)
  val alpha2 by animateAlphaWithDelay(delayUnit)
  val alpha3 by animateAlphaWithDelay(delayUnit * 2)

  Row {
    val spaceSize = 8.dp

    Dot(alpha1)
    Spacer(Modifier.width(spaceSize))
    Dot(alpha2)
    Spacer(Modifier.width(spaceSize))
    Dot(alpha3)
  }
}