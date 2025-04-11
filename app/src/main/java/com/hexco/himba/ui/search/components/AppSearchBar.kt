package com.hexco.himba.ui.search.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hexco.himba.ui.theme.background
import com.hexco.himba.ui.theme.primaryContainer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
  query: String,
  onQueryChange: (String) -> Unit,
  onSearch: (String) -> Unit,
  content: @Composable (ColumnScope.() -> Unit)
) {
  SearchBar(
    query = query,
    onQueryChange = onQueryChange,
    onSearch = onSearch,
    active = true,
    onActiveChange = {},
    colors = SearchBarDefaults.colors(
      containerColor = background,
      dividerColor = Color.LightGray.copy(alpha = .2f),
      inputFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = primaryContainer,
        unfocusedContainerColor = primaryContainer,
        focusedTrailingIconColor = Color.White,
        focusedTextColor = Color.White
      )
    ),
    trailingIcon = {
      if (!query.equals("")) {
        IconButton(
          onClick = {
            onQueryChange("")
          }
        ) {
          Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Close"
          )
        }
      }
    },
    placeholder = {
      Text("Search")
    },
    content = content
  )
}