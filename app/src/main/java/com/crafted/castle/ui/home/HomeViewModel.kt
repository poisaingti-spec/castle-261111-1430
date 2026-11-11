package com.crafted.castle.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crafted.castle.data.model.Content
import com.crafted.castle.data.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val featuredContent: Content? = null,
    val continueWatching: List<Content> = emptyList(),
    val trending: List<Content> = emptyList(),
    val newReleases: List<Content> = emptyList(),
    val topRated: List<Content> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadHomeData()
    }
    
    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // Load trending content
                val trendingResult = contentRepository.getTrending()
                trendingResult.onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        trending = response.items.take(10),
                        featuredContent = response.items.firstOrNull()
                    )
                }
                
                // Load new releases
                val newReleasesResult = contentRepository.getNewReleases()
                newReleasesResult.onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        newReleases = response.items.take(10)
                    )
                }
                
                // Load top rated
                val topRatedResult = contentRepository.getTopRated()
                topRatedResult.onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        topRated = response.items.take(10)
                    )
                }
                
                // Load continue watching from local database
                // (This would be implemented in a WatchHistoryRepository)
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to load content"
                )
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
    
    fun refresh() {
        loadHomeData()
    }
}