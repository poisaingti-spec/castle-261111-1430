package com.crafted.castle.ui.livetv

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

data class LiveTVUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val channels: List<Content> = emptyList()
)

@HiltViewModel
class LiveTVViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LiveTVUiState())
    val uiState: StateFlow<LiveTVUiState> = _uiState.asStateFlow()
    
    init {
        loadLiveTV()
    }
    
    fun loadLiveTV() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val result = contentRepository.getLiveTV()
                result.onSuccess { channels ->
                    _uiState.value = _uiState.value.copy(
                        channels = channels
                    )
                }.onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Failed to load live TV"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to load live TV"
                )
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}