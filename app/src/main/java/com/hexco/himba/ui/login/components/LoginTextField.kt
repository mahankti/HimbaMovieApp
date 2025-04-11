package com.hexco.himba.ui.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun LoginTextField(
  modifier: Modifier = Modifier,
  isPassword: Boolean = false,
  lable: String,
  value: String,
  onValueChange: (String) -> Unit,
  leadingIcon: ImageVector? = null,
  isError: Boolean = false,
  onDone: () -> Unit = {},
) {
  var passwordVisible by rememberSaveable {
    mutableStateOf(true)
  }

  OutlinedTextField(
    modifier = modifier
      .fillMaxWidth(),
    value = value,
    onValueChange = onValueChange,
    isError = isError,
    singleLine = true,
    label = {
      Text(text = lable)
    },
    leadingIcon = {
      if (leadingIcon != null) {
        Icon(
          imageVector = leadingIcon,
          contentDescription = lable,
          tint = Color.LightGray
        )
      }
    },
    trailingIcon = {
      if (isPassword) {
        IconButton(
          onClick = {
            passwordVisible = !passwordVisible
          }
        ) {
          Icon(
            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
            contentDescription = "Show/Hide"
          )
        }
      }
    },
    visualTransformation = if (isPassword && passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
    keyboardOptions = KeyboardOptions(
      keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text,
      imeAction = if (isPassword) ImeAction.Done else ImeAction.Next
    ),
    keyboardActions = KeyboardActions(
      onDone = { onDone() }
    ),
    shape = RoundedCornerShape(25),
    colors = TextFieldDefaults.colors(
      unfocusedIndicatorColor = Color.Gray,
      focusedIndicatorColor = Color.Gray,
      unfocusedContainerColor = Color.Transparent,
      focusedContainerColor = Color.Transparent,
      errorContainerColor = Color.Transparent,
      unfocusedLabelColor = Color.LightGray,
      focusedLabelColor = Color.LightGray,
      errorIndicatorColor = Color.Red,
    ),
  )
}