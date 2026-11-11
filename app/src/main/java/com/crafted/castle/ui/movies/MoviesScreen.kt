package com.crafted.castle.ui.movies

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crafted.castle.data.model.Content
import com.crafted.castle.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    onContentClick: (Content) -> Unit,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CastleTopAppBar(
            title = "Movies",
            showBackButton = false
        )
        
        if (uiState.isLoading) {
            LoadingScreen()
        } else if (uiState.error != null) {
            ErrorScreen(
                message = uiState.error!!,
                onRetry = { viewModel.loadMovies() }
            )
        } else if (uiState.movies.isEmpty()) {
            EmptyState(message = "No movies available")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 140.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.movies) { content ->
                    ContentCard(
                        content = content,
                        onClick = { onContentClick(content) }
                    )
                }
            }
        }
    }
}