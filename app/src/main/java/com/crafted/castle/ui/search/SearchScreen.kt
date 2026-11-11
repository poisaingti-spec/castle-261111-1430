package com.crafted.castle.ui.search

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
fun SearchScreen(
    onContentClick: (Content) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CastleTopAppBar(
            title = "Search",
            showBackButton = false
        )
        
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                if (query.length >= 2) {
                    viewModel.search(query)
                }
            },
            placeholder = {
                Text("Search movies, TV shows, sports…")
            },
            leadingIcon = {
                Icon(
                    androidx.compose.material.icons.filled.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        viewModel.clearResults()
                    }) {
                        Icon(
                            androidx.compose.material.icons.filled.Clear,
                            contentDescription = "Clear"
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true
        )
        
        if (uiState.isLoading) {
            LoadingScreen()
        } else if (uiState.error != null) {
            ErrorScreen(
                message = uiState.error!!,
                onRetry = { viewModel.search(searchQuery) }
            )
        } else if (searchQuery.isEmpty()) {
            // Show recent searches
            RecentSearchesSection(
                searches = uiState.recentSearches,
                onSearchClick = { query ->
                    searchQuery = query
                    viewModel.search(query)
                },
                onClearAll = { viewModel.clearSearchHistory() }
            )
        } else if (uiState.results.isEmpty()) {
            EmptyState(
                message = "No results found for \"$searchQuery\"",
                icon = "🔍"
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.results) { content ->
                    ContentCardWide(
                        content = content,
                        onClick = { onContentClick(content) }
                    )
                }
            }
        }
    }
}

@Composable
fun RecentSearchesSection(
    searches: List<String>,
    onSearchClick: (String) -> Unit,
    onClearAll: () -> Unit
) {
    if (searches.isEmpty()) {
        EmptyState(
            message = "No recent searches",
            icon = "📝"
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Searches",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                TextButton(onClick = onClearAll) {
                    Text("Clear All")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                searches.forEach { query ->
                    AssistChip(
                        onClick = { onSearchClick(query) },
                        label = { Text(query) },
                        leadingIcon = {
                            Icon(
                                androidx.compose.material.icons.filled.Search,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}