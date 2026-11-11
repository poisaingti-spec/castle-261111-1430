package com.crafted.castle.data.repository

import com.crafted.castle.data.api.ContentApi
import com.crafted.castle.data.local.ContentDao
import com.crafted.castle.data.model.Content
import com.crafted.castle.data.model.ContentListResponse
import com.crafted.castle.data.model.Category
import com.crafted.castle.data.model.SearchHistory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepository @Inject constructor(
    private val contentApi: ContentApi,
    private val contentDao: ContentDao
) {
    
    suspend fun getHomeContent(page: Int = 1, limit: Int = 20): Result<ContentListResponse> {
        return try {
            val response = contentApi.getHomeContent(page, limit)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getTrending(type: String = "all", page: Int = 1, limit: Int = 20): Result<ContentListResponse> {
        return try {
            val response = contentApi.getTrending(type, page, limit)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getNewReleases(page: Int = 1, limit: Int = 20): Result<ContentListResponse> {
        return try {
            val response = contentApi.getNewReleases(page, limit)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getContentDetail(contentId: String): Result<Content> {
        return try {
            val response = contentApi.getContentDetail(contentId)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun search(query: String, page: Int = 1, limit: Int = 20): Result<ContentListResponse> {
        return try {
            val response = contentApi.search(query, page, limit)
            if (response.success && response.data != null) {
                // Save to search history
                contentDao.insertSearchHistory(SearchHistory(query))
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getLiveTV(category: String? = null): Result<List<Content>> {
        return try {
            val response = contentApi.getLiveTV(category)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getSports(page: Int = 1, limit: Int = 20): Result<ContentListResponse> {
        return try {
            val response = contentApi.getSports(page, limit)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getCategories(): Result<List<Category>> {
        return try {
            val response = contentApi.getCategories()
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getRecommended(contentId: String, limit: Int = 10): Result<List<Content>> {
        return try {
            val response = contentApi.getRecommended(contentId, limit)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Local data operations
    fun getSearchHistory(): Flow<List<SearchHistory>> = contentDao.getSearchHistory()
    
    suspend fun clearSearchHistory() = contentDao.clearSearchHistory()
    
    suspend fun deleteSearchHistory(query: String) = contentDao.deleteSearchHistory(query)
}