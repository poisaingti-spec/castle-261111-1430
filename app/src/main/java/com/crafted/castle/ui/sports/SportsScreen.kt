package com.crafted.castle.ui.sports

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crafted.castle.data.model.Content
import com.crafted.castle.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsScreen(
    onContentClick: (Content) -> Unit,
    viewModel: SportsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CastleTopAppBar(
            title = "Sports",
            showBackButton = false
        )
        
        if (uiState.isLoading) {
            LoadingScreen()
        } else if (uiState.error != null) {
            ErrorScreen(
                message = uiState.error!!,
                onRetry = { viewModel.loadSports() }
            )
        } else if (uiState.sports.isEmpty()) {
            EmptyState(message = "No sports content available")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.sports) { content ->
                    ContentCardWide(
                        content = content,
                        onClick = { onContentClick(content) }
                    )
                }
            }
        }
    }
}