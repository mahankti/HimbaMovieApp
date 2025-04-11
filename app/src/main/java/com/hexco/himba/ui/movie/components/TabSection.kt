package com.hexco.himba.ui.movie.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hexco.himba.ui.theme.background
import com.hexco.himba.ui.theme.primaryColor
import com.hexco.himba.ui.util.LARGE_SPACE

data class TabItem(
  val title: String,
  val content: @Composable () -> Unit
)

@Composable
fun TabSection(
  modifier: Modifier = Modifier
) {
  val tabs = listOf(
    TabItem(
      title = "Episode"
    ) {
      Text("List Of Episodes")
    },
    TabItem(
      title = "Similiar"
    ) {
      Text("Similiar Shit")
    },
    TabItem(
      title = "About"
    ) {
      Text("H3xShot is GOD")
    },
  )
  var selectedTab by remember { mutableStateOf(0) }

  Column(
    modifier = modifier
  ) {
    TabRow(
      containerColor = background,
      divider = @Composable {},
      indicator = {
        TabRowDefaults.SecondaryIndicator(
          modifier = Modifier.tabIndicatorOffset(it[selectedTab]),
          color = primaryColor
        )
      },
      selectedTabIndex = selectedTab
    ) {
      tabs.forEachIndexed { index, tab ->
        Tab(
          selected = index == selectedTab,
          onClick = {
            selectedTab = index
          }
        ) {
          Text(
            modifier = Modifier.padding(LARGE_SPACE.dp),
            text = tab.title,
            color = if (selectedTab == index) primaryColor else Color.LightGray
          )
        }
      }
    }

    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      tabs[selectedTab].content()
    }
  }
}