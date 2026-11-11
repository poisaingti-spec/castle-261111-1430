package com.crafted.castle.ui.history

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crafted.castle.ui.components.*
import com.crafted.castle.ui.theme.CastleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            CastleTheme {
                HistoryScreen(
                    onBackPress = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onBackPress: () -> Unit
) {
    // In a real implementation, this would load from a WatchHistoryRepository
    val watchHistory = emptyList<com.crafted.castle.data.model.Content>()
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CastleTopAppBar(
            title = "Watch History",
            showBackButton = true,
            onBackClick = onBackPress
        )
        
        if (watchHistory.isEmpty()) {
            EmptyState(
                message = "No watch history yet",
                icon = "📺"
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(watchHistory.size) { index ->
                    // ContentCardWide(watchHistory[index])
                }
            }
        }
    }
}