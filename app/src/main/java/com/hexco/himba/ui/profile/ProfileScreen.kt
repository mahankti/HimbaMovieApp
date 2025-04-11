package com.hexco.himba.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hexco.himba.ui.theme.primaryContainer
import com.hexco.himba.ui.util.LARGE_SPACE
import com.hexco.himba.ui.util.MEDIUM_SPACE
import com.hexco.himba.ui.util.Uri2Image
import com.hexco.himba.ui.util.path2Uri

@Composable
fun ProfileScreen(
  viewModel: ProfileViewModel = hiltViewModel(),
  onLogout: () -> Unit,
) {
  val state by viewModel.state.collectAsState()

  Box(
    modifier = Modifier
      .statusBarsPadding()
      .padding(horizontal = LARGE_SPACE.dp)
      .fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Card(
      modifier = Modifier
        .padding(MEDIUM_SPACE.dp)
        .height(175.dp),
      shape = RoundedCornerShape(10),
      colors = CardDefaults.cardColors(
        containerColor = primaryContainer.copy(alpha = 0.6f)
      )
    ) {
      Row(
        modifier = Modifier.weight(0.7f)
      ) {
        Box(
          modifier = Modifier
            .fillMaxHeight()
            .weight(0.3f),
          contentAlignment = Alignment.Center
        ) {
          if (state.avatarPath.isEmpty()) {
            Icon(
              modifier = Modifier
                .size(64.dp)
                .background(color = Color.Gray.copy(alpha = 0.2f))
                .clip(CircleShape),
              imageVector = Icons.Default.Person,
              contentDescription = "Profile",
              tint = Color.White
            )
          } else {
            Uri2Image(
              modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
              url = path2Uri(imagePath = state.avatarPath)
            )
          }
        }

        Column(
          modifier = Modifier
            .fillMaxHeight()
            .weight(0.7f),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(text = "Name:", fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(LARGE_SPACE.dp))
            Text(text = state.name)
          }
          Spacer(Modifier.height(MEDIUM_SPACE.dp))
          Row(
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(text = "Username:", fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(LARGE_SPACE.dp))
            Text(text = state.username)
          }
        }
      }

      Row(
        modifier = Modifier
          .weight(0.3f)
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onLogout
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Default.Logout,
            contentDescription = "Logout"
          )
        }
      }
    }
  }
}