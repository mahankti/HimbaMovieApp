package com.hexco.himba

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hexco.himba.data.util.DataStoreManager
import com.hexco.himba.ui.main.MainScreen
import com.hexco.himba.ui.movie.MovieScreen
import com.hexco.himba.ui.movie_list.MovieList
import com.hexco.himba.ui.theme.HimbaTheme
import com.hexco.himba.ui.util.MainScreen
import com.hexco.himba.ui.util.MovieList
import com.hexco.himba.ui.util.MovieScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject lateinit var dataStoreManager: DataStoreManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge(
      statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
      navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
    )
    setContent {
      HimbaTheme {
        val navController = rememberNavController()
        val navigateToMovieScreen: (Int) -> Unit = {
          navController.navigate(MovieScreen(it))
        }
        val navigateUp: () -> Unit = {
          navController.navigateUp()
        }
        NavHost(
          navController = navController,
          startDestination = MainScreen,
          enterTransition = { EnterTransition.None },
          exitTransition = { ExitTransition.None }
        ) {
          composable<MainScreen> {
            MainScreen(
              dataStoreManager = dataStoreManager,
              navigateToMovieScreen = navigateToMovieScreen,
              navigateToMovieListScreen = {
                navController.navigate(MovieList(it))
              }
            )
          }
          composable<MovieScreen> {
            MovieScreen(
              sessionId = dataStoreManager.sessionId.collectAsState("").value,
              onBackClick = navigateUp
            )
          }
          composable<MovieList> {
            MovieList(
              navigateToMovieScreen = navigateToMovieScreen,
              onBackClick = navigateUp
            )
          }
        }
      }
    }
  }
}