package com.hexco.himba.ui.main.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.hexco.himba.ui.main.TabIndex
import com.hexco.himba.ui.theme.NoRippleInteractionSource
import com.hexco.himba.ui.util.FavoriteScreen
import com.hexco.himba.ui.util.HomeScreen
import com.hexco.himba.ui.util.ProfileScreen
import com.hexco.himba.ui.util.SearchScreen

data class NavBarItem(
  val lable: String,
  val selectedIcon: ImageVector,
  val unSelectedIcon: ImageVector,
  val hasNews: Boolean = false,
  val onClick: () -> Unit,
)

@Composable
fun MainNavigationBar(
  modifier: Modifier = Modifier,
  selectedTab: TabIndex,
  navigateTo: (Any) -> Unit
) {
  val navItems = listOf(
    NavBarItem(
      lable = "Home",
      selectedIcon = Icons.Filled.Home,
      unSelectedIcon = Icons.Outlined.Home,
      onClick = { navigateTo(HomeScreen) }
    ),
    NavBarItem(
      lable = "Search",
      selectedIcon = Icons.Filled.Search,
      unSelectedIcon = Icons.Outlined.Search,
      onClick = { navigateTo(SearchScreen) }
    ),
    NavBarItem(
      lable = "Favorite",
      selectedIcon = Icons.Filled.Favorite,
      unSelectedIcon = Icons.Outlined.Favorite,
      onClick = { navigateTo(FavoriteScreen) }
    ),
    NavBarItem(
      lable = "Me",
      selectedIcon = Icons.Filled.AccountCircle,
      unSelectedIcon = Icons.Outlined.AccountCircle,
      onClick = { navigateTo(ProfileScreen) }
    )
  )

  NavigationBar(
    modifier = modifier,
    containerColor = MaterialTheme.colorScheme.background
  ) {

    navItems.forEachIndexed { index, item ->
      val isSelected = index == selectedTab.ordinal
      NavigationBarItem(
        interactionSource = NoRippleInteractionSource,
        alwaysShowLabel = false,
        selected = isSelected,
        onClick = {
          item.onClick()
        },
        label = {
          Text(
            text = item.lable,
            maxLines = 1
          )
        },
        colors = NavigationBarItemDefaults.colors(
          indicatorColor = Color.Transparent,
          selectedIconColor = MaterialTheme.colorScheme.primary,
          selectedTextColor = MaterialTheme.colorScheme.primary,
        ),
        icon = {
          BadgedBox(
            badge = {
              if (item.hasNews) {
                Badge()
              }
            }
          ) {
            Icon(
              imageVector = if (isSelected) item.selectedIcon else item.unSelectedIcon,
              contentDescription = item.lable,
            )
          }
        }
      )
    }
  }
}