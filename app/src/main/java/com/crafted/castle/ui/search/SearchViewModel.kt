package com.crafted.castle.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crafted.castle.data.model.Content
import com.crafted.castle.data.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val results: List<Content> = emptyList(),
    val recentSearches: List<String> = emptyList()
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    
    private var searchJob: Job? = null
    
    init {
        loadRecentSearches()
    }
    
    fun search(query: String) {
        searchJob?.cancel()
        
        searchJob = viewModelScope.launch {
            delay(300) // Debounce
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val result = contentRepository.search(query)
                result.onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        results = response.items,
                        isLoading = false
                    )
                }.onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Search failed",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Search failed",
                    isLoading = false
                )
            }
        }
    }
    
    fun clearResults() {
        _uiState.value = _uiState.value.copy(results = emptyList())
    }
    
    private fun loadRecentSearches() {
        viewModelScope.launch {
            contentRepository.getSearchHistory().collect { history ->
                _uiState.value = _uiState.value.copy(
                    recentSearches = history.map { it.query }.distinct().take(10)
                )
            }
        }
    }
    
    fun clearSearchHistory() {
        viewModelScope.launch {
            contentRepository.clearSearchHistory()
        }
    }
}