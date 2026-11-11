package com.crafted.castle.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crafted.castle.data.model.Content
import com.crafted.castle.ui.components.*
import com.crafted.castle.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onContentClick: (Content) -> Unit,
    onSearchClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // Featured Content
            item {
                FeaturedContentSection(
                    content = uiState.featuredContent,
                    onContentClick = onContentClick
                )
            }
            
            // Continue Watching
            if (uiState.continueWatching.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = "Continue Watching",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                }
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.continueWatching) { content ->
                            ContentCard(
                                content = content,
                                onClick = { onContentClick(content) }
                            )
                        }
                    }
                }
            }
            
            // Trending Now
            item {
                SectionHeader(
                    title = "Trending Now",
                    onSeeAllClick = { /* Navigate to trending */ },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.trending) { content ->
                        ContentCard(
                            content = content,
                            onClick = { onContentClick(content) }
                        )
                    }
                }
            }
            
            // New Releases
            item {
                SectionHeader(
                    title = "New Releases",
                    onSeeAllClick = { /* Navigate to new releases */ },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.newReleases) { content ->
                        ContentCard(
                            content = content,
                            onClick = { onContentClick(content) }
                        )
                    }
                }
            }
            
            // Top Rated
            item {
                SectionHeader(
                    title = "Top Rated",
                    onSeeAllClick = { /* Navigate to top rated */ },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.topRated) { content ->
                        ContentCard(
                            content = content,
                            onClick = { onContentClick(content) }
                        )
                    }
                }
            }
        }
        
        // Top App Bar
        CastleTopAppBar(
            title = "castle",
            actions = {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        androidx.compose.material.icons.filled.Search,
                        contentDescription = "Search",
                        tint = Primary
                    )
                }
            }
        )
        
        if (uiState.isLoading) {
            LoadingScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
            )
        }
        
        if (uiState.error != null) {
            ErrorScreen(
                message = uiState.error!!,
                onRetry = { viewModel.refresh() },
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
        }
    }
}

@Composable
fun FeaturedContentSection(
    content: Content?,
    onContentClick: (Content) -> Unit
) {
    if (content == null) return
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        androidx.compose.foundation.Image(
            painter = coil.compose.rememberAsyncImagePainter(content.backdropUrl),
            contentDescription = content.title,
            modifier = Modifier.fillMaxSize()
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            androidx.compose.ui.graphics.Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        )
        
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = content.title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RatingBadge(rating = content.rating)
                Text(
                    text = "${content.releaseYear}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "•",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = content.genres.firstOrNull() ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Button(
                    onClick = { onContentClick(content) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary
                    )
                ) {
                    Icon(
                        androidx.compose.material.icons.filled.PlayArrow,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Watch Now")
                }
                
                OutlinedButton(
                    onClick = { /* Add to list */ },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Icon(
                        androidx.compose.material.icons.filled.Add,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("My List")
                }
            }
        }
    }
}