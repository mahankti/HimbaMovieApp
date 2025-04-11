package com.hexco.himba.ui.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hexco.himba.ui.theme.primaryColor
import com.hexco.himba.ui.util.components.LoadingDots

@Composable
fun LoginButton(
  modifier: Modifier = Modifier,
  lable: String,
  onClick: () -> Unit,
  isLoading: Boolean
) {
  Button(
    modifier = modifier
      .fillMaxWidth(),
    shape = RoundedCornerShape(50),
    enabled = !isLoading,
    colors = ButtonDefaults.buttonColors(
      containerColor = primaryColor,
      contentColor = Color.White
    ),
    onClick = onClick
  ) {
    if (isLoading) {
      LoadingDots()
    } else {
      Text(
        text = lable
      )
    }
  }
}